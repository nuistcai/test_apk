package com.alibaba.fastjson.support.spring.messaging;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import java.nio.charset.Charset;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

/* loaded from: classes.dex */
public class MappingFastJsonMessageConverter extends AbstractMessageConverter {
    private FastJsonConfig fastJsonConfig;

    public FastJsonConfig getFastJsonConfig() {
        return this.fastJsonConfig;
    }

    public void setFastJsonConfig(FastJsonConfig fastJsonConfig) {
        this.fastJsonConfig = fastJsonConfig;
    }

    public MappingFastJsonMessageConverter() {
        super(new MimeType("application", "json", Charset.forName("UTF-8")));
        this.fastJsonConfig = new FastJsonConfig();
    }

    protected boolean supports(Class<?> clazz) {
        return true;
    }

    protected boolean canConvertFrom(Message<?> message, Class<?> targetClass) {
        return supports(targetClass);
    }

    protected boolean canConvertTo(Object payload, MessageHeaders headers) {
        return supports(payload.getClass());
    }

    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        Object payload = message.getPayload();
        if (payload instanceof byte[]) {
            Object obj = JSON.parseObject((byte[]) payload, this.fastJsonConfig.getCharset(), targetClass, this.fastJsonConfig.getParserConfig(), this.fastJsonConfig.getParseProcess(), JSON.DEFAULT_PARSER_FEATURE, this.fastJsonConfig.getFeatures());
            return obj;
        }
        if (!(payload instanceof String)) {
            return null;
        }
        Object obj2 = JSON.parseObject((String) payload, targetClass, this.fastJsonConfig.getParserConfig(), this.fastJsonConfig.getParseProcess(), JSON.DEFAULT_PARSER_FEATURE, this.fastJsonConfig.getFeatures());
        return obj2;
    }

    protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
        if (byte[].class == getSerializedPayloadClass()) {
            if ((payload instanceof String) && JSON.isValid((String) payload)) {
                Object obj = ((String) payload).getBytes(this.fastJsonConfig.getCharset());
                return obj;
            }
            Object obj2 = JSON.toJSONBytesWithFastJsonConfig(this.fastJsonConfig.getCharset(), payload, this.fastJsonConfig.getSerializeConfig(), this.fastJsonConfig.getSerializeFilters(), this.fastJsonConfig.getDateFormat(), JSON.DEFAULT_GENERATE_FEATURE, this.fastJsonConfig.getSerializerFeatures());
            return obj2;
        }
        if ((payload instanceof String) && JSON.isValid((String) payload)) {
            return payload;
        }
        Object obj3 = JSON.toJSONString(payload, this.fastJsonConfig.getSerializeConfig(), this.fastJsonConfig.getSerializeFilters(), this.fastJsonConfig.getDateFormat(), JSON.DEFAULT_GENERATE_FEATURE, this.fastJsonConfig.getSerializerFeatures());
        return obj3;
    }
}