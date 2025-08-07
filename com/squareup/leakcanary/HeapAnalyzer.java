package com.squareup.leakcanary;

import android.os.Build;
import com.squareup.haha.perflib.ArrayInstance;
import com.squareup.haha.perflib.ClassInstance;
import com.squareup.haha.perflib.ClassObj;
import com.squareup.haha.perflib.Field;
import com.squareup.haha.perflib.HprofParser;
import com.squareup.haha.perflib.Instance;
import com.squareup.haha.perflib.RootObj;
import com.squareup.haha.perflib.RootType;
import com.squareup.haha.perflib.Snapshot;
import com.squareup.haha.perflib.Type;
import com.squareup.haha.perflib.io.HprofBuffer;
import com.squareup.haha.perflib.io.MemoryMappedFileBuffer;
import com.squareup.haha.trove.THashMap;
import com.squareup.haha.trove.TObjectProcedure;
import com.squareup.leakcanary.LeakTraceElement;
import com.squareup.leakcanary.ShortestPathFinder;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class HeapAnalyzer {
    private static final String ANONYMOUS_CLASS_NAME_PATTERN = "^.+\\$\\d+$";
    private final ExcludedRefs excludedRefs;

    public HeapAnalyzer(ExcludedRefs excludedRefs) {
        this.excludedRefs = excludedRefs;
    }

    public List<TrackedReference> findTrackedReferences(File heapDumpFile) {
        String str;
        String str2 = "name";
        if (!heapDumpFile.exists()) {
            throw new IllegalArgumentException("File does not exist: " + heapDumpFile);
        }
        try {
            HprofBuffer buffer = new MemoryMappedFileBuffer(heapDumpFile);
            HprofParser parser = new HprofParser(buffer);
            Snapshot snapshot = parser.parse();
            deduplicateGcRoots(snapshot);
            ClassObj refClass = snapshot.findClass(KeyedWeakReference.class.getName());
            List<TrackedReference> references = new ArrayList<>();
            for (Instance weakRef : refClass.getInstancesList()) {
                List<ClassInstance.FieldValue> values = HahaHelper.classInstanceValues(weakRef);
                String key = HahaHelper.asString(HahaHelper.fieldValue(values, "key"));
                String name = HahaHelper.hasField(values, str2) ? HahaHelper.asString(HahaHelper.fieldValue(values, str2)) : "(No name field)";
                Instance instance = (Instance) HahaHelper.fieldValue(values, "referent");
                if (instance == null) {
                    str = str2;
                } else {
                    String className = getClassName(instance);
                    List<String> fields = describeFields(instance);
                    str = str2;
                    references.add(new TrackedReference(key, name, className, fields));
                }
                str2 = str;
            }
            return references;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public AnalysisResult checkForLeak(File heapDumpFile, String referenceKey) {
        long analysisStartNanoTime = System.nanoTime();
        if (!heapDumpFile.exists()) {
            Exception exception = new IllegalArgumentException("File does not exist: " + heapDumpFile);
            return AnalysisResult.failure(exception, since(analysisStartNanoTime));
        }
        try {
            HprofBuffer buffer = new MemoryMappedFileBuffer(heapDumpFile);
            HprofParser parser = new HprofParser(buffer);
            Snapshot snapshot = parser.parse();
            deduplicateGcRoots(snapshot);
            Instance leakingRef = findLeakingReference(referenceKey, snapshot);
            if (leakingRef == null) {
                return AnalysisResult.noLeak(since(analysisStartNanoTime));
            }
            return findLeakTrace(analysisStartNanoTime, snapshot, leakingRef);
        } catch (Throwable e) {
            return AnalysisResult.failure(e, since(analysisStartNanoTime));
        }
    }

    void deduplicateGcRoots(Snapshot snapshot) {
        final THashMap<String, RootObj> uniqueRootMap = new THashMap<>();
        final Collection<RootObj> gcRoots = snapshot.getGCRoots();
        for (RootObj root : gcRoots) {
            String key = generateRootKey(root);
            if (!uniqueRootMap.containsKey(key)) {
                uniqueRootMap.put(key, root);
            }
        }
        gcRoots.clear();
        uniqueRootMap.forEach(new TObjectProcedure<String>() { // from class: com.squareup.leakcanary.HeapAnalyzer.1
            @Override // com.squareup.haha.trove.TObjectProcedure
            public boolean execute(String key2) {
                return gcRoots.add(uniqueRootMap.get(key2));
            }
        });
    }

    private String generateRootKey(RootObj root) {
        return String.format("%s@0x%08x", root.getRootType().getName(), Long.valueOf(root.getId()));
    }

    private Instance findLeakingReference(String key, Snapshot snapshot) {
        ClassObj refClass = snapshot.findClass(KeyedWeakReference.class.getName());
        List<String> keysFound = new ArrayList<>();
        for (Instance instance : refClass.getInstancesList()) {
            List<ClassInstance.FieldValue> values = HahaHelper.classInstanceValues(instance);
            String keyCandidate = HahaHelper.asString(HahaHelper.fieldValue(values, "key"));
            if (keyCandidate.equals(key)) {
                return (Instance) HahaHelper.fieldValue(values, "referent");
            }
            keysFound.add(keyCandidate);
        }
        throw new IllegalStateException("Could not find weak reference with key " + key + " in " + keysFound);
    }

    private AnalysisResult findLeakTrace(long analysisStartNanoTime, Snapshot snapshot, Instance leakingRef) throws ClassNotFoundException {
        long retainedSize;
        ShortestPathFinder pathFinder = new ShortestPathFinder(this.excludedRefs);
        ShortestPathFinder.Result result = pathFinder.findPath(snapshot, leakingRef);
        if (result.leakingNode != null) {
            LeakTrace leakTrace = buildLeakTrace(result.leakingNode);
            String className = leakingRef.getClassObj().getClassName();
            snapshot.computeDominators();
            Instance leakingInstance = result.leakingNode.instance;
            long retainedSize2 = leakingInstance.getTotalRetainedSize();
            if (Build.VERSION.SDK_INT > 25) {
                retainedSize = retainedSize2;
            } else {
                retainedSize = retainedSize2 + computeIgnoredBitmapRetainedSize(snapshot, leakingInstance);
            }
            return AnalysisResult.leakDetected(result.excludingKnownLeaks, className, leakTrace, retainedSize, since(analysisStartNanoTime));
        }
        return AnalysisResult.noLeak(since(analysisStartNanoTime));
    }

    private long computeIgnoredBitmapRetainedSize(Snapshot snapshot, Instance leakingInstance) {
        ArrayInstance mBufferInstance;
        long bitmapRetainedSize = 0;
        ClassObj bitmapClass = snapshot.findClass("android.graphics.Bitmap");
        for (Instance bitmapInstance : bitmapClass.getInstancesList()) {
            if (isIgnoredDominator(leakingInstance, bitmapInstance) && (mBufferInstance = (ArrayInstance) HahaHelper.fieldValue(HahaHelper.classInstanceValues(bitmapInstance), "mBuffer")) != null) {
                long bufferSize = mBufferInstance.getTotalRetainedSize();
                long bitmapSize = bitmapInstance.getTotalRetainedSize();
                if (bitmapSize < bufferSize) {
                    bitmapSize += bufferSize;
                }
                bitmapRetainedSize += bitmapSize;
            }
        }
        return bitmapRetainedSize;
    }

    private boolean isIgnoredDominator(Instance dominator, Instance instance) {
        boolean foundNativeRoot = false;
        do {
            Instance immediateDominator = instance.getImmediateDominator();
            if ((immediateDominator instanceof RootObj) && ((RootObj) immediateDominator).getRootType() == RootType.UNKNOWN) {
                instance = instance.getNextInstanceToGcRoot();
                foundNativeRoot = true;
            } else {
                instance = immediateDominator;
            }
            if (instance == null) {
                return false;
            }
        } while (instance != dominator);
        return foundNativeRoot;
    }

    private LeakTrace buildLeakTrace(LeakNode leakingNode) throws ClassNotFoundException {
        List<LeakTraceElement> elements = new ArrayList<>();
        for (LeakNode node = new LeakNode(null, null, leakingNode, null, null); node != null; node = node.parent) {
            LeakTraceElement element = buildLeakElement(node);
            if (element != null) {
                elements.add(0, element);
            }
        }
        return new LeakTrace(elements);
    }

    private LeakTraceElement buildLeakElement(LeakNode node) throws ClassNotFoundException {
        LeakTraceElement.Holder holderType;
        String extra;
        String extra2;
        if (node.parent == null) {
            return null;
        }
        Instance holder = node.parent.instance;
        if (holder instanceof RootObj) {
            return null;
        }
        LeakTraceElement.Type type = node.referenceType;
        String referenceName = node.referenceName;
        String extra3 = null;
        List<String> fields = describeFields(holder);
        String className = getClassName(holder);
        if (holder instanceof ClassObj) {
            holderType = LeakTraceElement.Holder.CLASS;
            extra = null;
        } else if (holder instanceof ArrayInstance) {
            holderType = LeakTraceElement.Holder.ARRAY;
            extra = null;
        } else {
            ClassObj classObj = holder.getClassObj();
            if (HahaHelper.extendsThread(classObj)) {
                holderType = LeakTraceElement.Holder.THREAD;
                String threadName = HahaHelper.threadName(holder);
                String extra4 = "(named '" + threadName + "')";
                extra = extra4;
            } else if (className.matches(ANONYMOUS_CLASS_NAME_PATTERN)) {
                String parentClassName = classObj.getSuperClassObj().getClassName();
                if (Object.class.getName().equals(parentClassName)) {
                    LeakTraceElement.Holder holderType2 = LeakTraceElement.Holder.OBJECT;
                    try {
                        Class<?> actualClass = Class.forName(classObj.getClassName());
                        Class<?>[] interfaces = actualClass.getInterfaces();
                        if (interfaces.length > 0) {
                            Class<?> implementedInterface = interfaces[0];
                            extra2 = "(anonymous implementation of " + implementedInterface.getName() + ")";
                        } else {
                            extra2 = "(anonymous subclass of java.lang.Object)";
                        }
                        extra3 = extra2;
                        holderType = holderType2;
                    } catch (ClassNotFoundException e) {
                        holderType = holderType2;
                    }
                } else {
                    holderType = LeakTraceElement.Holder.OBJECT;
                    extra3 = "(anonymous subclass of " + parentClassName + ")";
                }
                extra = extra3;
            } else {
                holderType = LeakTraceElement.Holder.OBJECT;
                extra = null;
            }
        }
        return new LeakTraceElement(referenceName, type, holderType, className, extra, node.exclusion, fields);
    }

    private List<String> describeFields(Instance instance) {
        List<String> fields = new ArrayList<>();
        if (instance instanceof ClassObj) {
            ClassObj classObj = (ClassObj) instance;
            for (Map.Entry<Field, Object> entry : classObj.getStaticFieldValues().entrySet()) {
                Field field = entry.getKey();
                Object value = entry.getValue();
                fields.add("static " + field.getName() + " = " + value);
            }
        } else if (instance instanceof ArrayInstance) {
            ArrayInstance arrayInstance = (ArrayInstance) instance;
            if (arrayInstance.getArrayType() == Type.OBJECT) {
                Object[] values = arrayInstance.getValues();
                for (int i = 0; i < values.length; i++) {
                    fields.add("[" + i + "] = " + values[i]);
                }
            }
        } else {
            ClassObj classObj2 = instance.getClassObj();
            Iterator<Map.Entry<Field, Object>> it = classObj2.getStaticFieldValues().entrySet().iterator();
            while (it.hasNext()) {
                fields.add("static " + HahaHelper.fieldToString(it.next()));
            }
            ClassInstance classInstance = (ClassInstance) instance;
            for (ClassInstance.FieldValue field2 : classInstance.getValues()) {
                fields.add(HahaHelper.fieldToString(field2));
            }
        }
        return fields;
    }

    private String getClassName(Instance instance) {
        if (instance instanceof ClassObj) {
            ClassObj classObj = (ClassObj) instance;
            String className = classObj.getClassName();
            return className;
        }
        if (instance instanceof ArrayInstance) {
            ArrayInstance arrayInstance = (ArrayInstance) instance;
            String className2 = arrayInstance.getClassObj().getClassName();
            return className2;
        }
        ClassObj classObj2 = instance.getClassObj();
        String className3 = classObj2.getClassName();
        return className3;
    }

    private long since(long analysisStartNanoTime) {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - analysisStartNanoTime);
    }
}