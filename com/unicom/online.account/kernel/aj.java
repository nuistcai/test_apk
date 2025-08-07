package com.unicom.online.account.kernel;

import android.util.Log;

/* loaded from: classes.dex */
public final class aj {
    private static boolean a = false;
    private static int b = 2;
    private static long c = 0;
    private static int d = 0;
    private static int e = 0;
    private static StringBuilder f = new StringBuilder();
    private static StringBuilder g = new StringBuilder();
    private static StringBuilder h = new StringBuilder();
    private static StringBuilder i = new StringBuilder();

    public static String a(int i2) {
        StringBuilder sb;
        switch (i2) {
            case 0:
                sb = g;
                break;
            case 1:
                sb = f;
                break;
            case 2:
                sb = h;
                break;
            case 3:
                sb = i;
                break;
            default:
                return "no info";
        }
        return sb.toString();
    }

    public static void a() {
        d = 0;
        e = 0;
        g.setLength(0);
        g.append("\n\n■★■★■★■★■★■★■★■★■★■\n\ncom debug info\n\n■★■★■★■★■★■★■★■★■★■\n\n");
        f.setLength(0);
        f.append("\n\n■★■★■★■★■★■★■★■★■★■\n\nall debug info\n\n■★■★■★■★■★■★■★■★■★■\n\n");
        h.setLength(0);
        h.append("\n\n■★■★■★■★■★■★■★■★■★■\n\nresult  info\n\n■★■★■★■★■★■★■★■★■★■\n\n");
        i.setLength(0);
        i.append("\n\n■★■★■★■★■★■★■★■★■★■\n\ncost time  info\n\n■★■★■★■★■★■★■★■★■★■\n\n");
    }

    public static void a(int i2, String str) {
        if (a) {
            if (i2 == 3) {
                StringBuilder sb = new StringBuilder("【");
                int i3 = e;
                e = i3 + 1;
                b(i2, sb.append(i3).append("】:").append(str).append("\n").toString());
                i2 = 1;
            }
            StringBuilder sb2 = new StringBuilder("【");
            int i4 = d;
            d = i4 + 1;
            b(i2, sb2.append(i4).append("】\n时间戳:").append(System.currentTimeMillis()).append("\n时间差:").append(System.currentTimeMillis() - c).append("\n数据:\n").append(str).append("\n\n").toString());
            c = System.currentTimeMillis();
        }
    }

    public static void a(Exception exc) {
        if (a) {
            exc.printStackTrace();
        }
    }

    public static void a(String str) {
        if (a) {
            Log.d("UniAccount", ac.d() + " " + str);
            a(0, str);
        }
    }

    public static void a(boolean z) {
        a = z;
    }

    private static void b(int i2, String str) {
        StringBuilder sb;
        if (a) {
            switch (i2) {
                case 0:
                    sb = g;
                    break;
                case 1:
                default:
                    f.append(str);
                case 2:
                    sb = h;
                    break;
                case 3:
                    sb = i;
                    break;
            }
            sb.append(str);
            f.append(str);
        }
    }

    public static void b(String str) {
        if (a) {
            Log.e("UniAccount", ac.d() + " " + str);
            a(0, str);
        }
    }

    public static void c(String str) {
        Log.e("UniAccount", ac.d() + " " + str);
        a(0, str);
    }
}