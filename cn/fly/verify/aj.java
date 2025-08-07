package cn.fly.verify;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class aj implements Serializable {
    public a a;
    public a b;
    public a c;
    private Integer d;
    private Integer e;
    private Integer f;
    private String g;

    public static class a implements Serializable {
        public int a;
        public String b;
        public String c;
        public int d;
        public Integer e;
        public String f;
        public String g;
        public int h;
        public int i;
        public int j;
        public int k;
        public int l;
        public ArrayList<String> m;

        public a(int i, String str, String str2, String str3, ArrayList<String> arrayList) {
            this.g = "LphSZLqaUeFdyaQq";
            this.h = 4000;
            this.i = 4000;
            this.j = 4000;
            this.k = 0;
            this.l = 0;
            this.a = i;
            this.b = str;
            this.c = str2;
            if (!TextUtils.isEmpty(str3)) {
                this.g = str3;
            }
            this.m = arrayList;
        }

        public a(int i, String str, String str2, String str3, ArrayList<String> arrayList, int i2, int i3, int i4, int i5, Integer num, String str4, int i6, int i7) {
            this(i, str, str2, str3, arrayList);
            this.i = i2;
            this.h = i3;
            this.j = i4;
            this.d = i5;
            if (num != null) {
                this.e = num;
            }
            if (!TextUtils.isEmpty(str4)) {
                this.f = str4;
            }
            this.k = i6;
            this.l = i7;
        }
    }

    public aj(a aVar, a aVar2, a aVar3) {
        this.a = aVar;
        this.b = aVar2;
        this.c = aVar3;
    }

    public a a(int i) {
        if (this.a != null && this.a.a == i) {
            return this.a;
        }
        if (this.b != null && this.b.a == i) {
            return this.b;
        }
        if (this.c == null || this.c.a != i) {
            return null;
        }
        return this.c;
    }

    public void a(Integer num) {
        this.d = num;
    }

    public void a(String str) {
        this.g = str;
    }

    public void b(Integer num) {
        this.e = num;
    }

    public void c(Integer num) {
        this.f = num;
    }
}