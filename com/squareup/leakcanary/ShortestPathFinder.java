package com.squareup.leakcanary;

import com.squareup.haha.perflib.ArrayInstance;
import com.squareup.haha.perflib.ClassInstance;
import com.squareup.haha.perflib.ClassObj;
import com.squareup.haha.perflib.Field;
import com.squareup.haha.perflib.HahaSpy;
import com.squareup.haha.perflib.Instance;
import com.squareup.haha.perflib.RootObj;
import com.squareup.haha.perflib.RootType;
import com.squareup.haha.perflib.Snapshot;
import com.squareup.haha.perflib.Type;
import com.squareup.leakcanary.LeakTraceElement;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/* loaded from: classes.dex */
final class ShortestPathFinder {
    private boolean canIgnoreStrings;
    private final ExcludedRefs excludedRefs;
    private final Queue<LeakNode> toVisitQueue = new LinkedList();
    private final Queue<LeakNode> toVisitIfNoPathQueue = new LinkedList();
    private final LinkedHashSet<Instance> toVisitSet = new LinkedHashSet<>();
    private final LinkedHashSet<Instance> toVisitIfNoPathSet = new LinkedHashSet<>();
    private final LinkedHashSet<Instance> visitedSet = new LinkedHashSet<>();

    ShortestPathFinder(ExcludedRefs excludedRefs) {
        this.excludedRefs = excludedRefs;
    }

    static final class Result {
        final boolean excludingKnownLeaks;
        final LeakNode leakingNode;

        Result(LeakNode leakingNode, boolean excludingKnownLeaks) {
            this.leakingNode = leakingNode;
            this.excludingKnownLeaks = excludingKnownLeaks;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0049, code lost:
    
        return new com.squareup.leakcanary.ShortestPathFinder.Result(r1, r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    Result findPath(Snapshot snapshot, Instance leakingRef) {
        LeakNode node;
        clearState();
        this.canIgnoreStrings = !isString(leakingRef);
        enqueueGcRoots(snapshot);
        boolean excludingKnownLeaks = false;
        LeakNode leakingNode = null;
        while (true) {
            if (this.toVisitQueue.isEmpty() && this.toVisitIfNoPathQueue.isEmpty()) {
                break;
            }
            if (!this.toVisitQueue.isEmpty()) {
                node = this.toVisitQueue.poll();
            } else {
                node = this.toVisitIfNoPathQueue.poll();
                if (node.exclusion == null) {
                    throw new IllegalStateException("Expected node to have an exclusion " + node);
                }
                excludingKnownLeaks = true;
            }
            if (node.instance == leakingRef) {
                leakingNode = node;
                break;
            }
            if (!checkSeen(node)) {
                if (node.instance instanceof RootObj) {
                    visitRootObj(node);
                } else if (node.instance instanceof ClassObj) {
                    visitClassObj(node);
                } else if (node.instance instanceof ClassInstance) {
                    visitClassInstance(node);
                } else if (node.instance instanceof ArrayInstance) {
                    visitArrayInstance(node);
                } else {
                    throw new IllegalStateException("Unexpected type for " + node.instance);
                }
            }
        }
    }

    private void clearState() {
        this.toVisitQueue.clear();
        this.toVisitIfNoPathQueue.clear();
        this.toVisitSet.clear();
        this.toVisitIfNoPathSet.clear();
        this.visitedSet.clear();
    }

    private void enqueueGcRoots(Snapshot snapshot) {
        for (RootObj rootObj : snapshot.getGCRoots()) {
            switch (rootObj.getRootType()) {
                case JAVA_LOCAL:
                    Instance thread = HahaSpy.allocatingThread(rootObj);
                    String threadName = HahaHelper.threadName(thread);
                    Exclusion params = this.excludedRefs.threadNames.get(threadName);
                    if (params == null || !params.alwaysExclude) {
                        enqueue(params, null, rootObj, null, null);
                        break;
                    } else {
                        break;
                    }
                    break;
                case INTERNED_STRING:
                case DEBUGGER:
                case INVALID_TYPE:
                case UNREACHABLE:
                case UNKNOWN:
                case FINALIZING:
                    break;
                case SYSTEM_CLASS:
                case VM_INTERNAL:
                case NATIVE_LOCAL:
                case NATIVE_STATIC:
                case THREAD_BLOCK:
                case BUSY_MONITOR:
                case NATIVE_MONITOR:
                case REFERENCE_CLEANUP:
                case NATIVE_STACK:
                case JAVA_STATIC:
                    enqueue(null, null, rootObj, null, null);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown root type:" + rootObj.getRootType());
            }
        }
    }

    private boolean checkSeen(LeakNode node) {
        return !this.visitedSet.add(node.instance);
    }

    private void visitRootObj(LeakNode node) {
        Exclusion exclusion;
        RootObj rootObj = (RootObj) node.instance;
        Instance child = rootObj.getReferredInstance();
        if (rootObj.getRootType() == RootType.JAVA_LOCAL) {
            Instance holder = HahaSpy.allocatingThread(rootObj);
            if (node.exclusion == null) {
                exclusion = null;
            } else {
                Exclusion exclusion2 = node.exclusion;
                exclusion = exclusion2;
            }
            LeakNode parent = new LeakNode(null, holder, null, null, null);
            enqueue(exclusion, parent, child, "<Java Local>", LeakTraceElement.Type.LOCAL);
            return;
        }
        enqueue(null, node, child, null, null);
    }

    private void visitClassObj(LeakNode node) {
        boolean visit;
        Exclusion params;
        ClassObj classObj = (ClassObj) node.instance;
        Map<String, Exclusion> ignoredStaticFields = this.excludedRefs.staticFieldNameByClassName.get(classObj.getClassName());
        for (Map.Entry<Field, Object> entry : classObj.getStaticFieldValues().entrySet()) {
            Field field = entry.getKey();
            if (field.getType() == Type.OBJECT) {
                String fieldName = field.getName();
                if (!fieldName.equals("$staticOverhead")) {
                    Instance child = (Instance) entry.getValue();
                    if (ignoredStaticFields != null && (params = ignoredStaticFields.get(fieldName)) != null) {
                        visit = false;
                        boolean visit2 = params.alwaysExclude;
                        if (!visit2) {
                            enqueue(params, node, child, fieldName, LeakTraceElement.Type.STATIC_FIELD);
                        }
                    } else {
                        visit = true;
                    }
                    if (visit) {
                        enqueue(null, node, child, fieldName, LeakTraceElement.Type.STATIC_FIELD);
                    }
                }
            }
        }
    }

    private void visitClassInstance(LeakNode node) {
        ClassInstance classInstance = (ClassInstance) node.instance;
        Map<String, Exclusion> ignoredFields = new LinkedHashMap<>();
        ClassObj superClassObj = classInstance.getClassObj();
        Exclusion classExclusion = null;
        for (ClassObj superClassObj2 = superClassObj; superClassObj2 != null; superClassObj2 = superClassObj2.getSuperClassObj()) {
            Exclusion params = this.excludedRefs.classNames.get(superClassObj2.getClassName());
            if (params != null && (classExclusion == null || !classExclusion.alwaysExclude)) {
                classExclusion = params;
            }
            Map<? extends String, ? extends Exclusion> map = this.excludedRefs.fieldNameByClassName.get(superClassObj2.getClassName());
            if (map != null) {
                ignoredFields.putAll(map);
            }
        }
        if (classExclusion != null && classExclusion.alwaysExclude) {
            return;
        }
        for (ClassInstance.FieldValue fieldValue : classInstance.getValues()) {
            Exclusion fieldExclusion = classExclusion;
            Field field = fieldValue.getField();
            if (field.getType() == Type.OBJECT) {
                Instance child = (Instance) fieldValue.getValue();
                String fieldName = field.getName();
                Exclusion params2 = ignoredFields.get(fieldName);
                if (params2 != null && (fieldExclusion == null || (params2.alwaysExclude && !fieldExclusion.alwaysExclude))) {
                    fieldExclusion = params2;
                }
                enqueue(fieldExclusion, node, child, fieldName, LeakTraceElement.Type.INSTANCE_FIELD);
            }
        }
    }

    private void visitArrayInstance(LeakNode node) {
        ArrayInstance arrayInstance = (ArrayInstance) node.instance;
        Type arrayType = arrayInstance.getArrayType();
        if (arrayType == Type.OBJECT) {
            Object[] values = arrayInstance.getValues();
            for (int i = 0; i < values.length; i++) {
                Instance child = (Instance) values[i];
                enqueue(null, node, child, "[" + i + "]", LeakTraceElement.Type.ARRAY_ENTRY);
            }
        }
    }

    private void enqueue(Exclusion exclusion, LeakNode parent, Instance child, String referenceName, LeakTraceElement.Type referenceType) {
        if (child == null || HahaHelper.isPrimitiveOrWrapperArray(child) || HahaHelper.isPrimitiveWrapper(child) || this.toVisitSet.contains(child)) {
            return;
        }
        boolean visitNow = exclusion == null;
        if (!visitNow && this.toVisitIfNoPathSet.contains(child)) {
            return;
        }
        if ((this.canIgnoreStrings && isString(child)) || this.visitedSet.contains(child)) {
            return;
        }
        LeakNode childNode = new LeakNode(exclusion, child, parent, referenceName, referenceType);
        if (visitNow) {
            this.toVisitSet.add(child);
            this.toVisitQueue.add(childNode);
        } else {
            this.toVisitIfNoPathSet.add(child);
            this.toVisitIfNoPathQueue.add(childNode);
        }
    }

    private boolean isString(Instance instance) {
        return instance.getClassObj() != null && instance.getClassObj().getClassName().equals(String.class.getName());
    }
}