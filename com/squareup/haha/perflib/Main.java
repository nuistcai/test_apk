package com.squareup.haha.perflib;

import com.squareup.haha.perflib.io.HprofBuffer;
import com.squareup.haha.perflib.io.MemoryMappedFileBuffer;
import java.io.File;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class Main {
    public static void main(String[] argv) {
        try {
            long start = System.nanoTime();
            HprofBuffer buffer = new MemoryMappedFileBuffer(new File(argv[0]));
            Snapshot snapshot = new HprofParser(buffer).parse();
            testClassesQuery(snapshot);
            testAllClassesQuery(snapshot);
            testFindInstancesOf(snapshot);
            testFindAllInstancesOf(snapshot);
            System.out.println("Memory stats: free=" + Runtime.getRuntime().freeMemory() + " / total=" + Runtime.getRuntime().totalMemory());
            System.out.println("Time: " + ((System.nanoTime() - start) / 1000000) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testClassesQuery(Snapshot snapshot) {
        String[] x = {"char[", "javax.", "org.xml.sax"};
        Map<String, Set<ClassObj>> someClasses = Queries.classes(snapshot, x);
        for (String thePackage : someClasses.keySet()) {
            System.out.println("------------------- " + thePackage);
            for (ClassObj theClass : someClasses.get(thePackage)) {
                System.out.println("     " + theClass.mClassName);
            }
        }
    }

    private static void testAllClassesQuery(Snapshot snapshot) {
        Map<String, Set<ClassObj>> allClasses = Queries.allClasses(snapshot);
        for (String thePackage : allClasses.keySet()) {
            System.out.println("------------------- " + thePackage);
            for (ClassObj theClass : allClasses.get(thePackage)) {
                System.out.println("     " + theClass.mClassName);
            }
        }
    }

    private static void testFindInstancesOf(Snapshot snapshot) {
        Instance[] instances = Queries.instancesOf(snapshot, "java.lang.String");
        System.out.println("There are " + instances.length + " Strings.");
    }

    private static void testFindAllInstancesOf(Snapshot snapshot) {
        Instance[] instances = Queries.allInstancesOf(snapshot, "android.graphics.drawable.Drawable");
        System.out.println("There are " + instances.length + " instances of Drawables and its subclasses.");
    }
}