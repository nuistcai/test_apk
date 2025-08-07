package com.cmic.gen.sdk.e;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

/* compiled from: SharedPreferencesUtil.java */
/* renamed from: com.cmic.gen.sdk.e.l, reason: use source file name */
/* loaded from: classes.dex */
public class SharedPreferencesUtil {
    private static Context a;

    public static void a(Context context) {
        a = context.getApplicationContext();
    }

    public static int a(String str, int i) {
        return a.getSharedPreferences("ssoconfigs", 0).getInt(MD5STo16Byte.a(str), i);
    }

    public static int a(String str, String str2, int i) {
        return a.getSharedPreferences(str, 0).getInt(MD5STo16Byte.a(str2), i);
    }

    public static long a(String str, long j) {
        return a.getSharedPreferences("ssoconfigs", 0).getLong(MD5STo16Byte.a(str), j);
    }

    public static long a(String str, String str2, long j) {
        return a.getSharedPreferences(str, 0).getLong(MD5STo16Byte.a(str2), j);
    }

    public static void a(String str, String str2) {
        SharedPreferences sharedPreferences = a.getSharedPreferences("ssoconfigs", 0);
        sharedPreferences.edit().putString(MD5STo16Byte.a(str), str2).commit();
    }

    public static void a(Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            SharedPreferences.Editor editorEdit = a.getSharedPreferences("ssoconfigs", 0).edit();
            for (String str : map.keySet()) {
                Object obj = map.get(str);
                String strA = MD5STo16Byte.a(str);
                if (obj instanceof String) {
                    editorEdit.putString(strA, (String) obj);
                } else if (obj instanceof Integer) {
                    editorEdit.putInt(strA, ((Integer) obj).intValue());
                } else if (obj instanceof Long) {
                    editorEdit.putLong(strA, ((Long) obj).longValue());
                } else if (obj instanceof Boolean) {
                    editorEdit.putBoolean(strA, ((Boolean) obj).booleanValue());
                }
            }
            editorEdit.commit();
        }
    }

    public static String b(String str, String str2) {
        return a.getSharedPreferences("ssoconfigs", 0).getString(MD5STo16Byte.a(str), str2);
    }

    public static String a(String str, String str2, String str3) {
        return a.getSharedPreferences(str, 0).getString(MD5STo16Byte.a(str2), str3);
    }

    public static void a(String str) {
        SharedPreferences sharedPreferences = a.getSharedPreferences("ssoconfigs", 0);
        sharedPreferences.edit().remove(MD5STo16Byte.a(str)).commit();
    }

    public static a a() {
        return new a(a.getSharedPreferences("ssoconfigs", 0).edit());
    }

    public static a b(String str) {
        return new a(a.getSharedPreferences(str, 0).edit());
    }

    /* compiled from: SharedPreferencesUtil.java */
    /* renamed from: com.cmic.gen.sdk.e.l$a */
    public static class a {
        private final SharedPreferences.Editor a;

        a(SharedPreferences.Editor editor) {
            this.a = editor;
        }

        public void a(String str, String str2) {
            this.a.putString(MD5STo16Byte.a(str), str2);
        }

        public void a(String str, long j) {
            this.a.putLong(MD5STo16Byte.a(str), j);
        }

        public void a(String str, int i) {
            this.a.putInt(MD5STo16Byte.a(str), i);
        }

        public void a() {
            this.a.apply();
        }

        public void b() {
            this.a.commit();
        }

        public void c() {
            this.a.clear();
        }

        public void a(String str) {
            this.a.remove(MD5STo16Byte.a(str));
        }
    }
}