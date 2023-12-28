package com.mattae.snl.plugins.flowable.web.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattae.snl.plugins.flowable.content.api.MetadataService;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntity;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.rest.variable.EngineRestVariable;
import org.flowable.content.api.ContentItem;
import org.flowable.content.api.ContentService;
import org.springframework.http.HttpStatus;

import java.util.*;

public class BaseMetadataResource {
    protected final ObjectMapper objectMapper;
    protected final ContentService contentService;
    protected final MetadataService metadataService;
    protected final ContentRestApiInterceptor restApiInterceptor;

    protected final ContentRestResponseFactory restResponseFactory;

    public BaseMetadataResource(ObjectMapper objectMapper,
                                ContentService contentService,
                                MetadataService metadataService,
                                ContentRestApiInterceptor restApiInterceptor,
                                ContentRestResponseFactory restResponseFactory) {
        this.objectMapper = objectMapper;
        this.contentService = contentService;
        this.metadataService = metadataService;
        this.restApiInterceptor = restApiInterceptor;
        this.restResponseFactory = restResponseFactory;
    }

    protected ContentItem getContentItemFromRequest(String contentItemId) {
        ContentItem contentItem = this.contentService.createContentItemQuery().id(contentItemId).singleResult();
        if (contentItem == null)
            throw new FlowableObjectNotFoundException("Could not find a content item with id '" + contentItemId + "'.");
        if (this.restApiInterceptor != null)
            this.restApiInterceptor.accessContentItemInfoById(contentItem);
        return contentItem;
    }

    public EngineRestVariable getMetadataFromRequest(ContentItem contentItem, String metadataName) {
        Object value = null;
        if (contentItem == null)
            throw new FlowableObjectNotFoundException("Could not find a content item", ContentItem.class);
        value = this.metadataService.getMetadataValue(contentItem.getId(), metadataName);
        if (value == null)
            throw new FlowableObjectNotFoundException("Case instance '" + contentItem.getId() + "' doesn't have a variable with name: '" + metadataName + "'.", MetadataInstanceEntity.class);
        return constructRestMetadataValue(metadataName, value, contentItem.getId());
    }

    protected EngineRestVariable constructRestMetadataValue(String variableName, Object value, String contentItemId) {
        return this.restResponseFactory.createRestMetadata(variableName, value, contentItemId);
    }

    protected Object createVariable(ContentItem contentItem, HttpServletRequest request, HttpServletResponse response) {
        List<EngineRestVariable> result = null;
        List<EngineRestVariable> inputVariables = new ArrayList<>();
        List<EngineRestVariable> resultVariables = new ArrayList<>();
        result = resultVariables;
        try {
            List<Object> variableObjects = this.objectMapper.readValue(request.getInputStream(), List.class);
            for (Object restObject : variableObjects) {
                EngineRestVariable restVariable = this.objectMapper.convertValue(restObject, EngineRestVariable.class);
                inputVariables.add(restVariable);
            }
        } catch (Exception e) {
            throw new FlowableIllegalArgumentException("Failed to serialize to a EngineRestVariable instance", e);
        }
        if (inputVariables.isEmpty())
            throw new FlowableIllegalArgumentException("Request didn't contain a list of variables to create.");
        Map<String, Object> variablesToSet = new HashMap<>();
        for (EngineRestVariable var : inputVariables) {
            if (var.getName() == null)
                throw new FlowableIllegalArgumentException("Variable name is required");
            Object actualVariableValue = this.restResponseFactory.getMetadataValue(var);
            variablesToSet.put(var.getName(), actualVariableValue);
            resultVariables.add(this.restResponseFactory.createRestMetadata(var.getName(), actualVariableValue, contentItem.getId()));
        }
        if (!variablesToSet.isEmpty())
            this.metadataService.setMetadataValues(contentItem.getId(), variablesToSet);
        response.setStatus(HttpStatus.CREATED.value());
        return result;
    }

    public void deleteAllVariables(ContentItem contentItem, HttpServletResponse response) {
        Collection<String> currentMetadataValues = this.metadataService.getMetadataValues(contentItem.getId()).keySet();
        this.metadataService.removeMetadataValues(contentItem.getId(), currentMetadataValues);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }

    protected EngineRestVariable setSimpleVariable(EngineRestVariable restVariable, ContentItem contentItem, boolean isNew) {
        if (restVariable.getName() == null)
            throw new FlowableIllegalArgumentException("Variable name is required");
        Object actualVariableValue = this.restResponseFactory.getMetadataValue(restVariable);
        setVariable(contentItem, restVariable.getName(), actualVariableValue, isNew);
        return constructRestMetadataValue(restVariable.getName(), actualVariableValue, contentItem.getId());
    }

    protected void setVariable(ContentItem contentItem, String name, Object value, boolean isNew) {
        this.metadataService.setMetadataValue(contentItem.getId(), name, value);
    }
}
