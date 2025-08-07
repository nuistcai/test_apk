package com.alibaba.fastjson.support.jaxrs;

import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.internal.util.PropertiesHelper;

/* loaded from: classes.dex */
public class FastJsonFeature implements Feature {
    private static final String JSON_FEATURE = FastJsonFeature.class.getSimpleName();

    public boolean configure(FeatureContext context) {
        Configuration config;
        String jsonFeature;
        try {
            config = context.getConfiguration();
            jsonFeature = (String) CommonProperties.getValue(config.getProperties(), config.getRuntimeType(), "jersey.config.jsonFeature", JSON_FEATURE, String.class);
        } catch (NoSuchMethodError e) {
        }
        if (!JSON_FEATURE.equalsIgnoreCase(jsonFeature)) {
            return false;
        }
        context.property(PropertiesHelper.getPropertyNameForRuntime("jersey.config.jsonFeature", config.getRuntimeType()), JSON_FEATURE);
        if (!config.isRegistered(FastJsonProvider.class)) {
            context.register(FastJsonProvider.class, new Class[]{MessageBodyReader.class, MessageBodyWriter.class});
        }
        return true;
    }
}