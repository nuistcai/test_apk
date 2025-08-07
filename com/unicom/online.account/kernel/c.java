package com.unicom.online.account.kernel;

/* loaded from: classes.dex */
public final class c {
    public int a;
    public long b;
    public long c;
    public int d;
    public g e;
    public g f;
    public g g;
    public g h;

    public final void a(g gVar) {
        this.d++;
        switch (this.d) {
            case 1:
                this.e = gVar;
                break;
            case 2:
                this.f = gVar;
                break;
            case 3:
                this.g = gVar;
                break;
            case 4:
                this.h = gVar;
                break;
        }
    }

    public final String toString() {
        return "{ifProtal:" + this.a + ", step1:" + this.e + ", step2:" + this.f + ", step3:" + this.g + ", step4:" + this.h + '}';
    }
}