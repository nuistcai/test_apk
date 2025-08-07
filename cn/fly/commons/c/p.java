package cn.fly.commons.c;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import cn.fly.commons.c.h;
import cn.fly.commons.w;
import cn.fly.tools.FlyLog;

/* loaded from: classes.dex */
public class p extends h {
    private a c;
    private String d;

    public p(Context context) {
        super(context);
        this.c = null;
        this.d = "100215079";
        if (!TextUtils.isEmpty(w.k)) {
            this.d = w.k;
        }
        FlyLog.getInstance().d("oamt vivo appid: " + this.d, new Object[0]);
    }

    @Override // cn.fly.commons.c.h
    protected h.b b() {
        h.b bVar = new h.b();
        bVar.a = a(0);
        return bVar;
    }

    public String a(int i) {
        Cursor cursorQuery;
        String strB = b(i);
        if (strB == null) {
            return null;
        }
        try {
            cursorQuery = this.a.getContentResolver().query(Uri.parse(strB), null, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToNext()) {
                        String string = cursorQuery.getString(cursorQuery.getColumnIndex(cn.fly.commons.o.a("005@ddIdg?dg2f")));
                        if (cursorQuery != null) {
                            try {
                                cursorQuery.close();
                            } catch (Throwable th) {
                            }
                        }
                        try {
                            c(i);
                        } catch (Throwable th2) {
                        }
                        return string;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        FlyLog.getInstance().d(th);
                        if (cursorQuery != null) {
                            try {
                                cursorQuery.close();
                            } catch (Throwable th4) {
                            }
                        }
                        c(i);
                        return null;
                    } catch (Throwable th5) {
                        if (cursorQuery != null) {
                            try {
                                cursorQuery.close();
                            } catch (Throwable th6) {
                            }
                        }
                        try {
                            c(i);
                            throw th5;
                        } catch (Throwable th7) {
                            throw th5;
                        }
                    }
                }
            }
            if (cursorQuery != null) {
                try {
                    cursorQuery.close();
                } catch (Throwable th8) {
                }
            }
            c(i);
        } catch (Throwable th9) {
            th = th9;
            cursorQuery = null;
        }
        return null;
    }

    private String b(int i) {
        if (i != 0) {
            return null;
        }
        return cn.fly.commons.o.a("051c]dk(eifeikllc@dkdfdldddidddkdldddffidleedcgldjdkdddidcOf-dj*lReedcRfei%diefdiCf)djeedcZl ghfdeefl");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z, int i) {
        try {
            String strA = a(i);
            if (i == 0) {
                a(strA);
            }
        } catch (Throwable th) {
        }
    }

    private void c(int i) {
        if (i == 0 && this.c == null) {
            this.c = new a(this, 0);
            this.a.getContentResolver().registerContentObserver(Uri.parse(b(0)), true, this.c);
        }
    }

    private static class a extends ContentObserver {
        private int a;
        private p b;

        public a(p pVar, int i) {
            super(null);
            this.a = i;
            this.b = pVar;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (this.b != null) {
                this.b.a(z, this.a);
            }
        }
    }
}