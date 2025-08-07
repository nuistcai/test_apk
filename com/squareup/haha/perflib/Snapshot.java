package com.squareup.haha.perflib;

import com.squareup.haha.guava.collect.ImmutableList;
import com.squareup.haha.perflib.analysis.Dominators;
import com.squareup.haha.perflib.analysis.ShortestDistanceVisitor;
import com.squareup.haha.perflib.analysis.TopologicalSort;
import com.squareup.haha.perflib.io.HprofBuffer;
import com.squareup.haha.trove.THashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class Snapshot {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int DEFAULT_HEAP_ID = 0;
    private static final String JAVA_LANG_CLASS = "java.lang.Class";
    public static final Instance SENTINEL_ROOT = new RootObj(RootType.UNKNOWN);
    final HprofBuffer mBuffer;
    Heap mCurrentHeap;
    private Dominators mDominators;
    private ImmutableList<Instance> mTopSort;
    private int[] mTypeSizes;
    ArrayList<Heap> mHeaps = new ArrayList<>();
    private THashSet<ClassObj> mReferenceClasses = new THashSet<>();
    private long mIdSizeMask = 4294967295L;

    public Snapshot(HprofBuffer buffer) {
        this.mBuffer = buffer;
        setToDefaultHeap();
    }

    public Heap setToDefaultHeap() {
        return setHeapTo(0, "default");
    }

    public Heap setHeapTo(int id, String name) {
        Heap heap = getHeap(id);
        Heap heap2 = heap;
        if (heap == null) {
            Heap heap3 = new Heap(id, name);
            heap2 = heap3;
            heap3.mSnapshot = this;
            this.mHeaps.add(heap2);
        }
        this.mCurrentHeap = heap2;
        return this.mCurrentHeap;
    }

    public int getHeapIndex(Heap heap) {
        return this.mHeaps.indexOf(heap);
    }

    public Heap getHeap(int id) {
        for (int i = 0; i < this.mHeaps.size(); i++) {
            if (this.mHeaps.get(i).getId() == id) {
                return this.mHeaps.get(i);
            }
        }
        return null;
    }

    public Heap getHeap(String name) {
        for (int i = 0; i < this.mHeaps.size(); i++) {
            if (name.equals(this.mHeaps.get(i).getName())) {
                return this.mHeaps.get(i);
            }
        }
        return null;
    }

    public Collection<Heap> getHeaps() {
        return this.mHeaps;
    }

    public Collection<RootObj> getGCRoots() {
        return this.mHeaps.get(0).mRoots;
    }

    public final void addStackFrame(StackFrame theFrame) {
        this.mCurrentHeap.addStackFrame(theFrame);
    }

    public final StackFrame getStackFrame(long id) {
        return this.mCurrentHeap.getStackFrame(id);
    }

    public final void addStackTrace(StackTrace theTrace) {
        this.mCurrentHeap.addStackTrace(theTrace);
    }

    public final StackTrace getStackTrace(int traceSerialNumber) {
        return this.mCurrentHeap.getStackTrace(traceSerialNumber);
    }

    public final StackTrace getStackTraceAtDepth(int traceSerialNumber, int depth) {
        return this.mCurrentHeap.getStackTraceAtDepth(traceSerialNumber, depth);
    }

    public final void addRoot(RootObj root) {
        this.mCurrentHeap.addRoot(root);
        root.setHeap(this.mCurrentHeap);
    }

    public final void addThread(ThreadObj thread, int serialNumber) {
        this.mCurrentHeap.addThread(thread, serialNumber);
    }

    public final ThreadObj getThread(int serialNumber) {
        return this.mCurrentHeap.getThread(serialNumber);
    }

    public final void setIdSize(int size) {
        int maxId = -1;
        for (int i = 0; i < Type.values().length; i++) {
            maxId = Math.max(Type.values()[i].getTypeId(), maxId);
        }
        if (maxId <= 0 || maxId > Type.LONG.getTypeId()) {
            throw new AssertionError();
        }
        this.mTypeSizes = new int[maxId + 1];
        Arrays.fill(this.mTypeSizes, -1);
        for (int i2 = 0; i2 < Type.values().length; i2++) {
            this.mTypeSizes[Type.values()[i2].getTypeId()] = Type.values()[i2].getSize();
        }
        this.mTypeSizes[Type.OBJECT.getTypeId()] = size;
        this.mIdSizeMask = (-1) >>> ((8 - size) << 3);
    }

    public final int getTypeSize(Type type) {
        return this.mTypeSizes[type.getTypeId()];
    }

    public final long getIdSizeMask() {
        return this.mIdSizeMask;
    }

    public final void addInstance(long id, Instance instance) {
        this.mCurrentHeap.addInstance(id, instance);
        instance.setHeap(this.mCurrentHeap);
    }

    public final void addClass(long id, ClassObj theClass) {
        this.mCurrentHeap.addClass(id, theClass);
        theClass.setHeap(this.mCurrentHeap);
    }

    public final Instance findInstance(long id) {
        for (int i = 0; i < this.mHeaps.size(); i++) {
            Instance instance = this.mHeaps.get(i).getInstance(id);
            if (instance != null) {
                return instance;
            }
        }
        return findClass(id);
    }

    public final ClassObj findClass(long id) {
        for (int i = 0; i < this.mHeaps.size(); i++) {
            ClassObj theClass = this.mHeaps.get(i).getClass(id);
            if (theClass != null) {
                return theClass;
            }
        }
        return null;
    }

    public final ClassObj findClass(String name) {
        for (int i = 0; i < this.mHeaps.size(); i++) {
            ClassObj theClass = this.mHeaps.get(i).getClass(name);
            if (theClass != null) {
                return theClass;
            }
        }
        return null;
    }

    public final Collection<ClassObj> findClasses(String name) {
        ArrayList<ClassObj> classObjs = new ArrayList<>();
        for (int i = 0; i < this.mHeaps.size(); i++) {
            classObjs.addAll(this.mHeaps.get(i).getClasses(name));
        }
        return classObjs;
    }

    public void resolveClasses() {
        ClassObj clazz = findClass(JAVA_LANG_CLASS);
        int javaLangClassSize = clazz != null ? clazz.getInstanceSize() : 0;
        Iterator i$ = this.mHeaps.iterator();
        while (i$.hasNext()) {
            Heap heap = i$.next();
            for (ClassObj classObj : heap.getClasses()) {
                ClassObj superClass = classObj.getSuperClassObj();
                if (superClass != null) {
                    superClass.addSubclass(classObj);
                }
                int classSize = javaLangClassSize;
                Field[] arr$ = classObj.mStaticFields;
                for (Field f : arr$) {
                    classSize += getTypeSize(f.getType());
                }
                classObj.setSize(classSize);
            }
            for (Instance instance : heap.getInstances()) {
                ClassObj classObj2 = instance.getClassObj();
                if (classObj2 != null) {
                    classObj2.addInstance(heap.getId(), instance);
                }
            }
        }
    }

    public void resolveReferences() {
        for (ClassObj classObj : findAllDescendantClasses(ClassObj.getReferenceClassName())) {
            classObj.setIsSoftReference();
            this.mReferenceClasses.add(classObj);
        }
    }

    public List<ClassObj> findAllDescendantClasses(String className) {
        Collection<ClassObj> ancestorClasses = findClasses(className);
        List<ClassObj> descendants = new ArrayList<>();
        for (ClassObj ancestor : ancestorClasses) {
            descendants.addAll(ancestor.getDescendantClasses());
        }
        return descendants;
    }

    public void computeDominators() {
        if (this.mDominators == null) {
            this.mTopSort = TopologicalSort.compute(getGCRoots());
            this.mDominators = new Dominators(this, this.mTopSort);
            this.mDominators.computeRetainedSizes();
            new ShortestDistanceVisitor().doVisit(getGCRoots());
        }
    }

    public List<Instance> getReachableInstances() {
        List<Instance> result = new ArrayList<>(this.mTopSort.size());
        Iterator i$ = this.mTopSort.iterator();
        while (i$.hasNext()) {
            Instance node = (Instance) i$.next();
            if (node.getImmediateDominator() != null) {
                result.add(node);
            }
        }
        return result;
    }

    public ImmutableList<Instance> getTopologicalOrdering() {
        return this.mTopSort;
    }

    public final void dumpInstanceCounts() {
        Iterator i$ = this.mHeaps.iterator();
        while (i$.hasNext()) {
            Heap heap = i$.next();
            System.out.println("+------------------ instance counts for heap: " + heap.getName());
            heap.dumpInstanceCounts();
        }
    }

    public final void dumpSizes() {
        Iterator i$ = this.mHeaps.iterator();
        while (i$.hasNext()) {
            Heap heap = i$.next();
            System.out.println("+------------------ sizes for heap: " + heap.getName());
            heap.dumpSizes();
        }
    }

    public final void dumpSubclasses() {
        Iterator i$ = this.mHeaps.iterator();
        while (i$.hasNext()) {
            Heap heap = i$.next();
            System.out.println("+------------------ subclasses for heap: " + heap.getName());
            heap.dumpSubclasses();
        }
    }
}