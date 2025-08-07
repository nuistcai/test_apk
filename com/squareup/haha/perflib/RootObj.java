package com.squareup.haha.perflib;

/* loaded from: classes.dex */
public class RootObj extends Instance {
    public static final String UNDEFINED_CLASS_NAME = "no class defined!!";
    int mIndex;
    int mThread;
    RootType mType;

    public RootObj(RootType type) {
        this(type, 0L, 0, null);
    }

    public RootObj(RootType type, long id) {
        this(type, id, 0, null);
    }

    public RootObj(RootType type, long id, int thread, StackTrace stack) {
        super(id, stack);
        this.mType = RootType.UNKNOWN;
        this.mType = type;
        this.mThread = thread;
    }

    public final String getClassName(Snapshot snapshot) {
        ClassObj theClass;
        if (this.mType == RootType.SYSTEM_CLASS) {
            theClass = snapshot.findClass(this.mId);
        } else {
            theClass = snapshot.findInstance(this.mId).getClassObj();
        }
        if (theClass == null) {
            return UNDEFINED_CLASS_NAME;
        }
        return theClass.mClassName;
    }

    @Override // com.squareup.haha.perflib.Instance
    public final void accept(Visitor visitor) {
        visitor.visitRootObj(this);
        Instance instance = getReferredInstance();
        if (instance != null) {
            visitor.visitLater(null, instance);
        }
    }

    public final String toString() {
        return String.format("%s@0x%08x", this.mType.getName(), Long.valueOf(this.mId));
    }

    public Instance getReferredInstance() {
        if (this.mType == RootType.SYSTEM_CLASS) {
            return this.mHeap.mSnapshot.findClass(this.mId);
        }
        return this.mHeap.mSnapshot.findInstance(this.mId);
    }

    public RootType getRootType() {
        return this.mType;
    }
}