package cn.fly.commons.c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import cn.fly.tools.FlyLog;
import cn.fly.tools.utils.DH;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class i extends h {
    private a c;
    private BroadcastReceiver d;

    public i(Context context) {
        super(context);
        this.c = new a(cn.fly.commons.a.l.a("004HelZeEejed"));
    }

    @Override // cn.fly.commons.c.h
    public synchronized String d() {
        if (this.a == null) {
            return null;
        }
        return a(this.a.getApplicationContext(), this.c, false);
    }

    private String a(Context context, a aVar, boolean z) {
        Cursor cursorQuery;
        String string;
        if (aVar == null) {
            return null;
        }
        if (!z && aVar.a()) {
            return aVar.c;
        }
        try {
            try {
                cursorQuery = context.getContentResolver().query(Uri.parse(cn.fly.commons.a.l.a("036d=el,fjgfjlmmd4elegemegIg=ejheehemfg@hWfdegWg(emelWkgfAejedgjedfi1m")), null, null, new String[]{aVar.a}, null);
            } catch (Throwable th) {
                th = th;
                cursorQuery = null;
            }
        } catch (Throwable th2) {
        }
        if (cursorQuery == null) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        }
        try {
            cursorQuery.moveToFirst();
            int columnIndex = cursorQuery.getColumnIndex(cn.fly.commons.a.l.a("005ZeeQeh@ehYg"));
            if (columnIndex >= 0) {
                string = cursorQuery.getString(columnIndex);
                aVar.a(string);
            } else {
                string = null;
            }
            if (!z) {
                int columnIndex2 = cursorQuery.getColumnIndex(cn.fly.commons.a.l.a("007gLfjUkWejek>gGed"));
                if (columnIndex2 >= 0) {
                    aVar.a(cursorQuery.getLong(columnIndex2));
                }
                int columnIndex3 = cursorQuery.getColumnIndex(cn.fly.commons.a.l.a("004d5eled5g"));
                if (columnIndex3 >= 0 && cursorQuery.getInt(columnIndex3) != 1000) {
                    e();
                }
            }
            return string;
        } catch (Throwable th3) {
            th = th3;
            try {
                FlyLog.getInstance().d(th);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            } finally {
                if (cursorQuery != null) {
                    try {
                        cursorQuery.close();
                    } catch (Throwable th4) {
                    }
                }
            }
        }
    }

    private void e() {
        try {
            if (this.d == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(cn.fly.commons.a.l.a("044d7elegemeg.gDejheehemfg=hSfdeg(g4emel^kgf,ejedemgefegdffhifheihihmhjfheiffgmeifeglgefhjehj"));
                this.d = new BroadcastReceiver() { // from class: cn.fly.commons.c.i.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent) {
                        String stringExtra;
                        ArrayList<String> stringArrayListExtra;
                        if (context != null && intent != null) {
                            try {
                                boolean zContains = false;
                                if (intent.getIntExtra(cn.fly.commons.a.l.a("016RelPkgfVffedfhel<j6ejfgfdhd*hePfk"), 0) == 2 && (stringArrayListExtra = intent.getStringArrayListExtra(cn.fly.commons.a.l.a("017(el,kgf*ffedhm,edBfiOe'fkGg+gfejgjAj"))) != null) {
                                    zContains = stringArrayListExtra.contains(context.getPackageName());
                                }
                                if (zContains && (stringExtra = intent.getStringExtra(cn.fly.commons.a.l.a("010*elFkgf,ffedgdfd6kg"))) != null && stringExtra.equals(cn.fly.commons.a.l.a("0049elSe<ejed"))) {
                                    i.this.c.a(0L);
                                }
                            } catch (Throwable th) {
                            }
                        }
                    }
                };
                if (DH.SyncMtd.getOSVersionIntForFly() < 33) {
                    this.a.registerReceiver(this.d, intentFilter, cn.fly.commons.a.l.a("048d;elegemegVgSejheehemfgHh%fdeg+gNemelBkgf0ejedem=kg1ekegejgjgjejelFf)emhihmhjfheiffgmeifeglgefhjehj"), null);
                } else {
                    this.a.registerReceiver(this.d, intentFilter, cn.fly.commons.a.l.a("048d=elegemegSgHejheehemfg6hJfdegUgXemel*kgf6ejedem^kgRekegejgjgjejelAf>emhihmhjfheiffgmeifeglgefhjehj"), null, 4);
                }
            }
        } catch (Throwable th) {
        }
    }

    public static class a {
        private String a;
        private long b;
        private String c;

        public a(String str) {
            this.a = str;
        }

        public void a(long j) {
            this.b = j;
        }

        public void a(String str) {
            this.c = str;
        }

        public boolean a() {
            return this.b > System.currentTimeMillis();
        }
    }
}