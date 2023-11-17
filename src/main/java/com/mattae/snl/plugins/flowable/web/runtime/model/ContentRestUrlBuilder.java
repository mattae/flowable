package com.mattae.snl.plugins.flowable.web.runtime.model;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.text.MessageFormat;

public class ContentRestUrlBuilder {

    protected String baseUrl = "";

    protected ContentRestUrlBuilder() {
    }

    protected ContentRestUrlBuilder(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Uses baseUrl as the base URL
     */
    public static ContentRestUrlBuilder usingBaseUrl(String baseUrl) {
        if (baseUrl == null) {
            throw new FlowableIllegalArgumentException("baseUrl can not be null");
        }
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        return new ContentRestUrlBuilder(baseUrl);
    }

    /**
     * Extracts the base URL from the request
     */
    public static ContentRestUrlBuilder fromRequest(HttpServletRequest request) {
        return usingBaseUrl(ServletUriComponentsBuilder.fromServletMapping(request).build().toUriString());
    }

    /**
     * Extracts the base URL from current request
     */
    public static ContentRestUrlBuilder fromCurrentRequest() {
        return usingBaseUrl(ServletUriComponentsBuilder.fromCurrentServletMapping().build().toUriString());
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String buildUrl(String[] fragments, Object... arguments) {
        return new StringBuilder(baseUrl).append("/").append(MessageFormat.format(StringUtils.join(fragments, '/'), arguments)).toString();
    }
}
