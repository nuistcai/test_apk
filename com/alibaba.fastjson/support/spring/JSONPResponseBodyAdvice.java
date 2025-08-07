package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.support.spring.annotation.ResponseJSONP;
import com.alibaba.fastjson.util.IOUtils;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@Order(Integer.MIN_VALUE)
/* loaded from: classes.dex */
public class JSONPResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    public final Log logger = LogFactory.getLog(getClass());

    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return FastJsonHttpMessageConverter.class.isAssignableFrom(converterType) && (returnType.getContainingClass().isAnnotationPresent(ResponseJSONP.class) || returnType.hasMethodAnnotation(ResponseJSONP.class));
    }

    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ResponseJSONP responseJsonp;
        String callbackMethodName;
        ResponseJSONP responseJsonp2 = (ResponseJSONP) returnType.getMethodAnnotation(ResponseJSONP.class);
        if (responseJsonp2 != null) {
            responseJsonp = responseJsonp2;
        } else {
            responseJsonp = (ResponseJSONP) returnType.getContainingClass().getAnnotation(ResponseJSONP.class);
        }
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String callbackMethodName2 = servletRequest.getParameter(responseJsonp.callback());
        if (IOUtils.isValidJsonpQueryParam(callbackMethodName2)) {
            callbackMethodName = callbackMethodName2;
        } else {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Invalid jsonp parameter value:" + callbackMethodName2);
            }
            callbackMethodName = null;
        }
        JSONPObject jsonpObject = new JSONPObject(callbackMethodName);
        jsonpObject.addParameter(body);
        beforeBodyWriteInternal(jsonpObject, selectedContentType, returnType, request, response);
        return jsonpObject;
    }

    public void beforeBodyWriteInternal(JSONPObject jsonpObject, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
    }

    protected MediaType getContentType(MediaType contentType, ServerHttpRequest request, ServerHttpResponse response) {
        return FastJsonHttpMessageConverter.APPLICATION_JAVASCRIPT;
    }
}