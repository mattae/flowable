package com.mattae.snl.plugins.flowable.content.engine.impl.repository;

import org.flowable.common.engine.api.query.QueryProperty;

import java.util.HashMap;
import java.util.Map;

public class DocumentDefinitionQueryProperty implements QueryProperty {
    private static final Map<String, DocumentDefinitionQueryProperty> properties = new HashMap<>();
    public static final DocumentDefinitionQueryProperty DOCUMENT_DEFINITION_KEY = new DocumentDefinitionQueryProperty("RES.KEY_");
    public static final DocumentDefinitionQueryProperty DOCUMENT_DEFINITION_CATEGORY = new DocumentDefinitionQueryProperty("RES.CATEGORY_");
    public static final DocumentDefinitionQueryProperty DOCUMENT_DEFINITION_ID = new DocumentDefinitionQueryProperty("RES.ID_");
    public static final DocumentDefinitionQueryProperty DOCUMENT_DEFINITION_VERSION = new DocumentDefinitionQueryProperty("RES.VERSION_");
    public static final DocumentDefinitionQueryProperty DOCUMENT_DEFINITION_NAME = new DocumentDefinitionQueryProperty("RES.NAME_");
    public static final DocumentDefinitionQueryProperty DOCUMENT_DEFINITION_DEPLOYMENT_ID = new DocumentDefinitionQueryProperty("RES.DEPLOYMENT_ID_");
    public static final DocumentDefinitionQueryProperty DOCUMENT_DEFINITION_TENANT_ID = new DocumentDefinitionQueryProperty("RES.TENANT_ID_");
    private static final long serialVersionUID = 1L;
    private String name;

    public DocumentDefinitionQueryProperty(String name) {
        this.name = name;
        properties.put(name, this);
    }

    public static DocumentDefinitionQueryProperty findByName(String propertyName) {
        return properties.get(propertyName);
    }

    public String getName() {
        return this.name;
    }
}
