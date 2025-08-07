package com.alibaba.fastjson.support.jaxrs;

import javax.annotation.Priority;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;

@Priority(1999)
/* loaded from: classes.dex */
public class FastJsonAutoDiscoverable implements AutoDiscoverable {
    public static final String FASTJSON_AUTO_DISCOVERABLE = "fastjson.auto.discoverable";
    public static volatile boolean autoDiscover;

    static {
        autoDiscover = true;
        try {
            autoDiscover = Boolean.parseBoolean(System.getProperty(FASTJSON_AUTO_DISCOVERABLE, String.valueOf(autoDiscover)));
        } catch (Throwable th) {
        }
    }

    public void configure(FeatureContext context) {
        Configuration config = context.getConfiguration();
        if (!config.isRegistered(FastJsonFeature.class) && autoDiscover) {
            context.register(FastJsonFeature.class);
        }
    }
}