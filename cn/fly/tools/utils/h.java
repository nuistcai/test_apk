package cn.fly.tools.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes.dex */
public class h {
    private final SharedPreferences a;
    private final SharedPreferences.Editor b;

    public h(Context context, String str) {
        this.a = context.getSharedPreferences(str, 0);
        this.b = this.a.edit();
    }

    public void a(String str, String str2) {
        this.b.putString(str, str2).apply();
    }

    public String b(String str, String str2) {
        return this.a.getString(str, str2);
    }

    public void a(String str, int i) {
        this.b.putInt(str, i).apply();
    }

    public int b(String str, int i) {
        return this.a.getInt(str, i);
    }

    public void a() {
        this.b.clear().apply();
    }
}