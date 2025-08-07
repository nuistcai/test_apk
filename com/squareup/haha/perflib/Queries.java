package com.squareup.haha.perflib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/* loaded from: classes.dex */
public class Queries {
    private static final String DEFAULT_PACKAGE = "<default>";

    public static Map<String, Set<ClassObj>> allClasses(Snapshot snapshot) {
        return classes(snapshot, null);
    }

    public static Map<String, Set<ClassObj>> classes(Snapshot snapshot, String[] strArr) {
        String strSubstring;
        TreeMap treeMap = new TreeMap();
        TreeSet<ClassObj> treeSet = new TreeSet();
        Iterator<Heap> it = snapshot.mHeaps.iterator();
        while (it.hasNext()) {
            treeSet.addAll(it.next().getClasses());
        }
        if (strArr != null) {
            int length = strArr.length;
            Iterator it2 = treeSet.iterator();
            while (it2.hasNext()) {
                String string = ((ClassObj) it2.next()).toString();
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    if (!string.startsWith(strArr[i])) {
                        i++;
                    } else {
                        it2.remove();
                        break;
                    }
                }
            }
        }
        for (ClassObj classObj : treeSet) {
            int iLastIndexOf = classObj.mClassName.lastIndexOf(46);
            if (iLastIndexOf != -1) {
                strSubstring = classObj.mClassName.substring(0, iLastIndexOf);
            } else {
                strSubstring = DEFAULT_PACKAGE;
            }
            Set treeSet2 = (Set) treeMap.get(strSubstring);
            if (treeSet2 == null) {
                treeSet2 = new TreeSet();
                treeMap.put(strSubstring, treeSet2);
            }
            treeSet2.add(classObj);
        }
        return treeMap;
    }

    public static Collection<ClassObj> commonClasses(Snapshot first, Snapshot second) {
        Collection<ClassObj> classes = new ArrayList<>();
        Iterator i$ = first.getHeaps().iterator();
        while (i$.hasNext()) {
            for (ClassObj clazz : i$.next().getClasses()) {
                if (second.findClass(clazz.getClassName()) != null) {
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }

    public static ClassObj findClass(Snapshot snapshot, String name) {
        return snapshot.findClass(name);
    }

    public static Instance[] instancesOf(Snapshot snapshot, String baseClassName) {
        ClassObj theClass = snapshot.findClass(baseClassName);
        if (theClass == null) {
            throw new IllegalArgumentException("Class not found: " + baseClassName);
        }
        List<Instance> instances = theClass.getInstancesList();
        return (Instance[]) instances.toArray(new Instance[instances.size()]);
    }

    public static Instance[] allInstancesOf(Snapshot snapshot, String baseClassName) {
        ClassObj theClass = snapshot.findClass(baseClassName);
        if (theClass == null) {
            throw new IllegalArgumentException("Class not found: " + baseClassName);
        }
        ArrayList<ClassObj> classList = new ArrayList<>();
        classList.add(theClass);
        classList.addAll(traverseSubclasses(theClass));
        ArrayList<Instance> instanceList = new ArrayList<>();
        Iterator i$ = classList.iterator();
        while (i$.hasNext()) {
            ClassObj someClass = i$.next();
            instanceList.addAll(someClass.getInstancesList());
        }
        Instance[] result = new Instance[instanceList.size()];
        instanceList.toArray(result);
        return result;
    }

    private static ArrayList<ClassObj> traverseSubclasses(ClassObj base) {
        ArrayList<ClassObj> result = new ArrayList<>();
        for (ClassObj subclass : base.mSubclasses) {
            result.add(subclass);
            result.addAll(traverseSubclasses(subclass));
        }
        return result;
    }

    public static Instance findObject(Snapshot snapshot, String id) throws NumberFormatException {
        long id2 = Long.parseLong(id, 16);
        return snapshot.findInstance(id2);
    }

    public static Collection<RootObj> getRoots(Snapshot snapshot) {
        HashSet<RootObj> result = new HashSet<>();
        Iterator i$ = snapshot.mHeaps.iterator();
        while (i$.hasNext()) {
            Heap heap = i$.next();
            result.addAll(heap.mRoots);
        }
        return result;
    }

    public static final Instance[] newInstances(Snapshot older, Snapshot newer) {
        ArrayList<Instance> resultList = new ArrayList<>();
        Iterator i$ = newer.mHeaps.iterator();
        while (i$.hasNext()) {
            Heap newHeap = i$.next();
            Heap oldHeap = older.getHeap(newHeap.getName());
            if (oldHeap != null) {
                for (Instance instance : newHeap.getInstances()) {
                    Instance oldInstance = oldHeap.getInstance(instance.mId);
                    if (oldInstance == null || instance.getClassObj() != oldInstance.getClassObj()) {
                        resultList.add(instance);
                    }
                }
            }
        }
        Instance[] resultArray = new Instance[resultList.size()];
        return (Instance[]) resultList.toArray(resultArray);
    }
}