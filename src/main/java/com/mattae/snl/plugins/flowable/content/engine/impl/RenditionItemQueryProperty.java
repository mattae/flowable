package com.mattae.snl.plugins.flowable.content.engine.impl;

import org.flowable.common.engine.api.query.QueryProperty;

import java.util.HashMap;
import java.util.Map;

public class RenditionItemQueryProperty implements QueryProperty {
    public static final RenditionItemQueryProperty CREATED_DATE = new RenditionItemQueryProperty("RES.CREATED_");
    public static final RenditionItemQueryProperty TENANT_ID = new RenditionItemQueryProperty("RES.TENANT_ID_");
    private static final long serialVersionUID = 1L;
    private static final Map<String, RenditionItemQueryProperty> properties = new HashMap<>();
    private String name;

    public RenditionItemQueryProperty(String name) {
        this.name = name;
        properties.put(name, this);
    }

    public static RenditionItemQueryProperty findByName(String propertyName) {
        return properties.get(propertyName);
    }

    public String getName() {
        return this.name;
    }
}
