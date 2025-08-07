package com.squareup.haha.perflib;

import com.squareup.haha.trove.TIntObjectHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/* loaded from: classes.dex */
public class ClassObj extends Instance implements Comparable<ClassObj> {
    long mClassLoaderId;
    final String mClassName;
    Field[] mFields;
    TIntObjectHashMap<HeapData> mHeapData;
    private int mInstanceSize;
    private boolean mIsSoftReference;
    Field[] mStaticFields;
    private final long mStaticFieldsOffset;
    Set<ClassObj> mSubclasses;
    long mSuperClassId;

    public static class HeapData {
        public int mShallowSize = 0;
        public List<Instance> mInstances = new ArrayList();
    }

    public ClassObj(long id, StackTrace stack, String className, long staticFieldsOffset) {
        super(id, stack);
        this.mIsSoftReference = false;
        this.mHeapData = new TIntObjectHashMap<>();
        this.mSubclasses = new HashSet();
        this.mClassName = className;
        this.mStaticFieldsOffset = staticFieldsOffset;
    }

    public final void addSubclass(ClassObj subclass) {
        this.mSubclasses.add(subclass);
    }

    public final Set<ClassObj> getSubclasses() {
        return this.mSubclasses;
    }

    public final void dumpSubclasses() {
        for (ClassObj subclass : this.mSubclasses) {
            System.out.println("     " + subclass.mClassName);
        }
    }

    public final String toString() {
        return this.mClassName.replace('/', '.');
    }

    public final void addInstance(int heapId, Instance instance) {
        if (instance instanceof ClassInstance) {
            instance.setSize(this.mInstanceSize);
        }
        HeapData heapData = this.mHeapData.get(heapId);
        HeapData heapData2 = heapData;
        if (heapData == null) {
            heapData2 = new HeapData();
            this.mHeapData.put(heapId, heapData2);
        }
        heapData2.mInstances.add(instance);
        heapData2.mShallowSize += instance.getSize();
    }

    public final void setSuperClassId(long superClass) {
        this.mSuperClassId = superClass;
    }

    public final void setClassLoaderId(long classLoader) {
        this.mClassLoaderId = classLoader;
    }

    public int getAllFieldsCount() {
        int result = 0;
        for (ClassObj clazz = this; clazz != null; clazz = clazz.getSuperClassObj()) {
            result += clazz.getFields().length;
        }
        return result;
    }

    public Field[] getFields() {
        return this.mFields;
    }

    public void setFields(Field[] fields) {
        this.mFields = fields;
    }

    public void setStaticFields(Field[] staticFields) {
        this.mStaticFields = staticFields;
    }

    public void setInstanceSize(int size) {
        this.mInstanceSize = size;
    }

    public int getInstanceSize() {
        return this.mInstanceSize;
    }

    public int getShallowSize(int heapId) {
        if (this.mHeapData.get(heapId) == null) {
            return 0;
        }
        return this.mHeapData.get(heapId).mShallowSize;
    }

    public void setIsSoftReference() {
        this.mIsSoftReference = true;
    }

    @Override // com.squareup.haha.perflib.Instance
    public boolean getIsSoftReference() {
        return this.mIsSoftReference;
    }

    public Map<Field, Object> getStaticFieldValues() {
        Map<Field, Object> result = new HashMap<>();
        getBuffer().setPosition(this.mStaticFieldsOffset);
        int numEntries = readUnsignedShort();
        for (int i = 0; i < numEntries; i++) {
            Field f = this.mStaticFields[i];
            readId();
            readUnsignedByte();
            Object value = readValue(f.getType());
            result.put(f, value);
        }
        return result;
    }

    public final void dump() {
        ClassObj superClassObj = this;
        while (true) {
            System.out.println("+----------  ClassObj dump for: " + superClassObj.mClassName);
            System.out.println("+-----  Static fields");
            Map<Field, Object> staticFields = superClassObj.getStaticFieldValues();
            for (Field field : staticFields.keySet()) {
                System.out.println(field.getName() + ": " + field.getType() + " = " + staticFields.get(field));
            }
            System.out.println("+-----  Instance fields");
            Field[] arr$ = superClassObj.mFields;
            for (Field field2 : arr$) {
                System.out.println(field2.getName() + ": " + field2.getType());
            }
            if (superClassObj.getSuperClassObj() != null) {
                superClassObj = superClassObj.getSuperClassObj();
            } else {
                return;
            }
        }
    }

    public final String getClassName() {
        return this.mClassName;
    }

    @Override // com.squareup.haha.perflib.Instance
    public final void accept(Visitor visitor) {
        visitor.visitClassObj(this);
        for (Map.Entry<Field, Object> entry : getStaticFieldValues().entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Instance) {
                if (!this.mReferencesAdded) {
                    ((Instance) value).addReference(entry.getKey(), this);
                }
                visitor.visitLater(this, (Instance) value);
            }
        }
        this.mReferencesAdded = true;
    }

    @Override // java.lang.Comparable
    public final int compareTo(ClassObj o) {
        if (getId() == o.getId()) {
            return 0;
        }
        int nameCompareResult = this.mClassName.compareTo(o.mClassName);
        if (nameCompareResult != 0) {
            return nameCompareResult;
        }
        return getId() - o.getId() > 0 ? 1 : -1;
    }

    public final boolean equals(Object o) {
        return (o instanceof ClassObj) && compareTo((ClassObj) o) == 0;
    }

    public int hashCode() {
        return this.mClassName.hashCode();
    }

    Object getStaticField(Type type, String name) {
        return getStaticFieldValues().get(new Field(type, name));
    }

    public ClassObj getSuperClassObj() {
        return this.mHeap.mSnapshot.findClass(this.mSuperClassId);
    }

    public Instance getClassLoader() {
        return this.mHeap.mSnapshot.findInstance(this.mClassLoaderId);
    }

    public List<Instance> getInstancesList() {
        int count = getInstanceCount();
        ArrayList<Instance> resultList = new ArrayList<>(count);
        int[] arr$ = this.mHeapData.keys();
        for (int heapId : arr$) {
            resultList.addAll(getHeapInstances(heapId));
        }
        return resultList;
    }

    public List<Instance> getHeapInstances(int heapId) {
        HeapData result = this.mHeapData.get(heapId);
        return result == null ? new ArrayList(0) : result.mInstances;
    }

    public int getHeapInstancesCount(int heapId) {
        HeapData result = this.mHeapData.get(heapId);
        if (result == null) {
            return 0;
        }
        return result.mInstances.size();
    }

    public int getInstanceCount() {
        int count = 0;
        Object[] arr$ = this.mHeapData.getValues();
        for (Object heapStat : arr$) {
            count += ((HeapData) heapStat).mInstances.size();
        }
        return count;
    }

    public int getShallowSize() {
        int size = 0;
        Object[] arr$ = this.mHeapData.getValues();
        for (Object heapStat : arr$) {
            size += ((HeapData) heapStat).mShallowSize;
        }
        return size;
    }

    public static String getReferenceClassName() {
        return "java.lang.ref.Reference";
    }

    public List<ClassObj> getDescendantClasses() {
        List<ClassObj> descendants = new ArrayList<>();
        Stack<ClassObj> searchStack = new Stack<>();
        searchStack.push(this);
        while (!searchStack.isEmpty()) {
            ClassObj classObj = searchStack.pop();
            descendants.add(classObj);
            for (ClassObj subClass : classObj.getSubclasses()) {
                searchStack.push(subClass);
            }
        }
        return descendants;
    }
}