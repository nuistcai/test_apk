package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@Order
/* loaded from: classes.dex */
public class FastJsonViewResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    /* renamed from: beforeBodyWrite, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ Object m6beforeBodyWrite(Object obj, MethodParameter methodParameter, MediaType mediaType, Class cls, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        return beforeBodyWrite(obj, methodParameter, mediaType, (Class<? extends HttpMessageConverter<?>>) cls, serverHttpRequest, serverHttpResponse);
    }

    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return FastJsonHttpMessageConverter.class.isAssignableFrom(converterType) && returnType.hasMethodAnnotation(FastJsonView.class);
    }

    public FastJsonContainer beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        FastJsonContainer container = getOrCreateContainer(body);
        beforeBodyWriteInternal(container, selectedContentType, returnType, request, response);
        return container;
    }

    private FastJsonContainer getOrCreateContainer(Object body) {
        return body instanceof FastJsonContainer ? (FastJsonContainer) body : new FastJsonContainer(body);
    }

    protected void beforeBodyWriteInternal(FastJsonContainer container, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        FastJsonView annotation = (FastJsonView) returnType.getMethodAnnotation(FastJsonView.class);
        FastJsonFilter[] include = annotation.include();
        FastJsonFilter[] exclude = annotation.exclude();
        PropertyPreFilters filters = new PropertyPreFilters();
        for (FastJsonFilter item : include) {
            filters.addFilter(item.clazz(), item.props());
        }
        for (FastJsonFilter item2 : exclude) {
            filters.addFilter(item2.clazz(), new String[0]).addExcludes(item2.props());
        }
        container.setFilters(filters);
    }
}