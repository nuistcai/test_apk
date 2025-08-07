package cn.fly.mcl.c;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.fly.commons.o;

/* loaded from: classes.dex */
public class c {
    private a a;

    public c(Context context) {
        this.a = new a(context.getApplicationContext());
    }

    public void a(String str, long j) {
        try {
            SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("workId", str);
            contentValues.put("expireTime", Long.valueOf(j));
            writableDatabase.replace(a.a, null, contentValues);
            writableDatabase.close();
        } catch (Throwable th) {
            b.a().a(th);
        }
    }

    public long a(String str) {
        try {
            SQLiteDatabase readableDatabase = this.a.getReadableDatabase();
            Cursor cursor = (Cursor) SQLiteDatabase.class.getMethod(o.a("008?dj[dUfgjjdg%f@djec"), String.class, String[].class).invoke(readableDatabase, "select expireTime from " + a.a + " where workId = ?", new String[]{str});
            if (cursor.moveToFirst()) {
                return cursor.getLong(cursor.getColumnIndex("expireTime"));
            }
            if (cursor != null) {
                cursor.close();
            }
            readableDatabase.close();
            return 0L;
        } catch (Throwable th) {
            b.a().a(th);
            return 0L;
        }
    }

    public void a() {
        try {
            SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
            SQLiteDatabase.class.getDeclaredMethod(o.a("007fJei9fc0eljjfe"), String.class, Object[].class).invoke(writableDatabase, "delete from " + a.a + " where expireTime < ?", new String[]{System.currentTimeMillis() + ""});
            writableDatabase.close();
        } catch (Throwable th) {
            b.a().a(th);
        }
    }

    public void b(String str) {
        try {
            SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
            SQLiteDatabase.class.getDeclaredMethod(o.a("007fXei7fcOeljjfe"), String.class, Object[].class).invoke(writableDatabase, "delete from " + a.a + " where workId = ?", new String[]{str});
            writableDatabase.close();
        } catch (Throwable th) {
            b.a().a(th);
        }
    }
}