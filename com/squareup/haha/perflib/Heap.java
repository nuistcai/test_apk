package com.squareup.haha.perflib;

import com.squareup.haha.guava.collect.ArrayListMultimap;
import com.squareup.haha.guava.collect.Multimap;
import com.squareup.haha.trove.TIntObjectHashMap;
import com.squareup.haha.trove.TLongObjectHashMap;
import com.squareup.haha.trove.TObjectProcedure;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes.dex */
public class Heap {
    private final int mId;
    private final String mName;
    Snapshot mSnapshot;
    TLongObjectHashMap<StackFrame> mFrames = new TLongObjectHashMap<>();
    TIntObjectHashMap<StackTrace> mTraces = new TIntObjectHashMap<>();
    ArrayList<RootObj> mRoots = new ArrayList<>();
    TIntObjectHashMap<ThreadObj> mThreads = new TIntObjectHashMap<>();
    TLongObjectHashMap<ClassObj> mClassesById = new TLongObjectHashMap<>();
    Multimap<String, ClassObj> mClassesByName = ArrayListMultimap.create();
    private final TLongObjectHashMap<Instance> mInstances = new TLongObjectHashMap<>();

    public Heap(int id, String name) {
        this.mId = id;
        this.mName = name;
    }

    public int getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public final void addStackFrame(StackFrame theFrame) {
        this.mFrames.put(theFrame.mId, theFrame);
    }

    public final StackFrame getStackFrame(long id) {
        return this.mFrames.get(id);
    }

    public final void addStackTrace(StackTrace theTrace) {
        this.mTraces.put(theTrace.mSerialNumber, theTrace);
    }

    public final StackTrace getStackTrace(int traceSerialNumber) {
        return this.mTraces.get(traceSerialNumber);
    }

    public final StackTrace getStackTraceAtDepth(int traceSerialNumber, int depth) {
        StackTrace trace = this.mTraces.get(traceSerialNumber);
        if (trace == null) {
            return trace;
        }
        return trace.fromDepth(depth);
    }

    public final void addRoot(RootObj root) {
        root.mIndex = this.mRoots.size();
        this.mRoots.add(root);
    }

    public final void addThread(ThreadObj thread, int serialNumber) {
        this.mThreads.put(serialNumber, thread);
    }

    public final ThreadObj getThread(int serialNumber) {
        return this.mThreads.get(serialNumber);
    }

    public final void addInstance(long id, Instance instance) {
        this.mInstances.put(id, instance);
    }

    public final Instance getInstance(long id) {
        return this.mInstances.get(id);
    }

    public final void addClass(long id, ClassObj theClass) {
        this.mClassesById.put(id, theClass);
        this.mClassesByName.put(theClass.mClassName, theClass);
    }

    public final ClassObj getClass(long id) {
        return this.mClassesById.get(id);
    }

    public final ClassObj getClass(String name) {
        Collection<ClassObj> classes = this.mClassesByName.get(name);
        if (classes.size() == 1) {
            return classes.iterator().next();
        }
        return null;
    }

    public final Collection<ClassObj> getClasses(String name) {
        return this.mClassesByName.get(name);
    }

    public final void dumpInstanceCounts() {
        Object[] arr$ = this.mClassesById.getValues();
        for (Object obj : arr$) {
            ClassObj theClass = (ClassObj) obj;
            int count = theClass.getInstanceCount();
            if (count > 0) {
                System.out.println(theClass + ": " + count);
            }
        }
    }

    public final void dumpSubclasses() {
        Object[] arr$ = this.mClassesById.getValues();
        for (Object obj : arr$) {
            ClassObj theClass = (ClassObj) obj;
            if (theClass.mSubclasses.size() > 0) {
                System.out.println(theClass);
                theClass.dumpSubclasses();
            }
        }
    }

    public final void dumpSizes() {
        Object[] arr$ = this.mClassesById.getValues();
        for (Object obj : arr$) {
            ClassObj theClass = (ClassObj) obj;
            int size = 0;
            for (Instance instance : theClass.getHeapInstances(getId())) {
                size += instance.getCompositeSize();
            }
            if (size > 0) {
                System.out.println(theClass + ": base " + theClass.getSize() + ", composite " + size);
            }
        }
    }

    public Collection<ClassObj> getClasses() {
        return this.mClassesByName.values();
    }

    public Collection<Instance> getInstances() {
        final ArrayList<Instance> result = new ArrayList<>(this.mInstances.size());
        this.mInstances.forEachValue(new TObjectProcedure<Instance>() { // from class: com.squareup.haha.perflib.Heap.1
            @Override // com.squareup.haha.trove.TObjectProcedure
            public boolean execute(Instance instance) {
                result.add(instance);
                return true;
            }
        });
        return result;
    }

    public int getInstancesCount() {
        return this.mInstances.size();
    }
}