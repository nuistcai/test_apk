package cn.fly.tools.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import cn.fly.commons.n;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.PublicMemberKeeper;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class SQLiteHelper implements PublicMemberKeeper {
    public static SingleTableDB getDatabase(Context context, String str) {
        String path;
        if (context == null) {
            path = null;
        } else {
            path = context.getDatabasePath(str).getPath();
        }
        return getDatabase(path, str);
    }

    public static SingleTableDB getDatabase(String str, String str2) {
        return new SingleTableDB(str, str2);
    }

    public static long insert(SingleTableDB singleTableDB, ContentValues contentValues) throws Throwable {
        singleTableDB.a();
        return singleTableDB.c.replace(singleTableDB.c(), null, contentValues);
    }

    public static int delete(SingleTableDB singleTableDB, String str, String[] strArr) throws Throwable {
        singleTableDB.a();
        return singleTableDB.c.delete(singleTableDB.c(), str, strArr);
    }

    public static int update(SingleTableDB singleTableDB, ContentValues contentValues, String str, String[] strArr) throws Throwable {
        singleTableDB.a();
        return singleTableDB.c.update(singleTableDB.c(), contentValues, str, strArr);
    }

    public static Cursor query(SingleTableDB singleTableDB, String[] strArr, String str, String[] strArr2, String str2) throws Throwable {
        singleTableDB.a();
        return singleTableDB.c.query(singleTableDB.c(), strArr, str, strArr2, null, null, str2);
    }

    public static void close(SingleTableDB singleTableDB) {
        singleTableDB.b();
    }

    public static class SingleTableDB implements PublicMemberKeeper {
        private String a;
        private String b;
        private SQLiteDatabase c;
        private LinkedHashMap<String, String> d;
        private HashMap<String, Boolean> e;
        private String f;
        private boolean g;

        private SingleTableDB(String str, String str2) {
            this.a = str;
            this.b = str2;
            this.d = new LinkedHashMap<>();
            this.e = new HashMap<>();
        }

        public void addField(String str, String str2, boolean z) {
            if (this.c == null) {
                this.d.put(str, str2);
                this.e.put(str, Boolean.valueOf(z));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:39:0x009a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void a() throws Throwable {
            boolean z;
            if (TextUtils.isEmpty(this.a)) {
                throw new Throwable("path is null");
            }
            File file = new File(this.a);
            Cursor cursorQuery = null;
            if (this.c != null && !file.exists()) {
                this.c.close();
                try {
                    File parentFile = file.getParentFile();
                    if (parentFile != null && (!parentFile.exists() || !parentFile.isDirectory())) {
                        parentFile.delete();
                        parentFile.mkdirs();
                    }
                } catch (Throwable th) {
                }
                this.c = null;
            }
            if (this.c == null) {
                if (!file.exists()) {
                    try {
                        File parentFile2 = file.getParentFile();
                        if (parentFile2 != null && (!parentFile2.exists() || !parentFile2.isDirectory())) {
                            parentFile2.delete();
                            parentFile2.mkdirs();
                            file.createNewFile();
                        }
                    } catch (Throwable th2) {
                    }
                }
                this.c = SQLiteDatabase.openOrCreateDatabase(file, (SQLiteDatabase.CursorFactory) null);
                try {
                    cursorQuery = this.c.query(n.a("013,dgbc?e bgAgd!bfbd3bZdgCgd%bh"), null, n.a("017gUca1hdOgggfgdTbc,bagd%cbYbdJd6gggf"), new String[]{n.a("005gb%ddHed"), this.b}, null, null, null);
                    if (cursorQuery != null) {
                        if (cursorQuery.getCount() > 0) {
                            z = false;
                        } else {
                            z = true;
                        }
                    }
                    if (z) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("create table  ").append(this.b).append("(");
                        for (Map.Entry<String, String> entry : this.d.entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            boolean zBooleanValue = this.e.get(key).booleanValue();
                            boolean zEquals = key.equals(this.f);
                            boolean z2 = zEquals ? this.g : false;
                            sb.append(key).append(" ").append(value);
                            sb.append(zBooleanValue ? " not null" : "");
                            sb.append(zEquals ? " primary key" : "");
                            sb.append(z2 ? " autoincrement," : ",");
                        }
                        sb.replace(sb.length() - 1, sb.length(), ");");
                        try {
                            SQLiteDatabase.class.getMethod(n.a("007dXcg_daTcjhhdc"), String.class).invoke(this.c, sb.toString());
                        } catch (Throwable th3) {
                            FlyLog.getInstance().d(th3);
                        }
                    }
                } finally {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b() {
            if (this.c != null) {
                this.c.close();
                this.c = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String c() {
            return this.b;
        }
    }
}