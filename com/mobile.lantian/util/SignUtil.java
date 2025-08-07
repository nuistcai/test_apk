package com.mobile.lantian.util;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class SignUtil {
    private static String charset = "utf8";

    public static String getSign(Map<String, Object> data, String secret) {
        if (data == null) {
            return null;
        }
        Map<String, Object> mappingList = new TreeMap<>(data);
        final StringBuilder plainText = new StringBuilder();
        mappingList.forEach(new BiConsumer() { // from class: com.mobile.lantian.util.SignUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SignUtil.lambda$getSign$0(plainText, (String) obj, obj2);
            }
        });
        String substring = plainText.substring(0, plainText.length() - 1);
        return Md5Util.MD5Encode(substring + secret, charset);
    }

    static /* synthetic */ void lambda$getSign$0(StringBuilder plainText, String k, Object v) {
        if ("sign".equals(k) || BaseUtils.isEmpty(v)) {
            return;
        }
        plainText.append(String.format("%s=%s&", k, v));
    }
}