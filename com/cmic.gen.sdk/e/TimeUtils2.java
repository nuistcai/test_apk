package com.cmic.gen.sdk.e;

import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: TimeUtils.java */
/* renamed from: com.cmic.gen.sdk.e.p, reason: use source file name */
/* loaded from: classes.dex */
public class TimeUtils2 {
    public static String a() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
    }
}