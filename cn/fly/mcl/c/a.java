package cn.fly.mcl.c;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cn.fly.commons.m;

/* loaded from: classes.dex */
public class a extends SQLiteOpenHelper {
    public static final String a = m.a("003*fhhkgl");
    private static final String b = "CREATE TABLE " + a + " (workId TEXT PRIMARY KEY,expireTime INTEGER )";

    public a(Context context) {
        super(context, "elp_msg.db", (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            SQLiteDatabase.class.getMethod(m.a("007h(gk*he gnllhg"), String.class).invoke(sQLiteDatabase, b);
        } catch (Throwable th) {
            b.a().a(th);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}