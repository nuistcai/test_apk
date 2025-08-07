package cn.fly.commons;

import com.mob.commons.MOBLINK;
import com.mob.commons.MOBPUSH;
import com.mob.commons.SECVERIFY;
import com.mob.commons.SHARESDK;
import com.mob.commons.SMSSDK;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class p {
    public static final List<Object> a = new LinkedList();

    static {
        try {
            a.add(SHARESDK.class);
        } catch (Throwable th) {
        }
        try {
            a.add(SMSSDK.class);
        } catch (Throwable th2) {
        }
        try {
            a.add(MOBLINK.class);
        } catch (Throwable th3) {
        }
        try {
            a.add(MOBPUSH.class);
        } catch (Throwable th4) {
        }
        try {
            a.add(SECVERIFY.class);
        } catch (Throwable th5) {
        }
        try {
            a.add(FLYVERIFY.class);
        } catch (Throwable th6) {
        }
    }
}