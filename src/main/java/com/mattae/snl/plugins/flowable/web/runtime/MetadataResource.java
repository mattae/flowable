package com.mattae.snl.plugins.flowable.web.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattae.snl.plugins.flowable.content.api.MetadataInstance;
import com.mattae.snl.plugins.flowable.content.api.MetadataService;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.rest.variable.EngineRestVariable;
import org.flowable.content.api.ContentItem;
import org.flowable.content.api.ContentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class MetadataResource extends BaseMetadataResource {
    public MetadataResource(ObjectMapper objectMapper,
                            ContentService contentService,
                            MetadataService metadataService,
                            ContentRestApiInterceptor restApiInterceptor,
                            ContentRestResponseFactory restResponseFactory) {
        super(objectMapper, contentService, metadataService, restApiInterceptor, restResponseFactory);
    }

    @GetMapping(value = {"/content-service/content-items/{contentItemId}/metadata/{metadataName}"}, produces = {"application/json"})
    public EngineRestVariable getMetadataValue(@PathVariable("contentItemId") String contentItemId, @PathVariable("metadataName") String metadataName, @RequestParam(value = "scope", required = false) String scope, HttpServletRequest request) {
        ContentItem contentItem = getContentItemFromRequest(contentItemId);
        return getMetadataFromRequest(contentItem, metadataName);
    }

    @PutMapping(value = {"/content-service/content-items/{contentItemId}/metadata/{metadataName}"}, produces = {"application/json"}, consumes = {"application/json", "multipart/form-data"})
    public EngineRestVariable updateMetadataValue(@PathVariable("contentItemId") String contentItemId, @PathVariable("metadataName") String metadataName, HttpServletRequest request) {
        ContentItem contentItem = getContentItemFromRequest(contentItemId);
        EngineRestVariable restVariable = null;
        try {
            restVariable = this.objectMapper.readValue(request.getInputStream(), EngineRestVariable.class);
        } catch (Exception e) {
            throw new FlowableIllegalArgumentException("request body could not be transformed to a RestVariable instance.");
        }
        if (restVariable == null)
            throw new FlowableException("Invalid body was supplied");
        if (!restVariable.getName().equals(metadataName))
            throw new FlowableIllegalArgumentException("Metadata name in the body should be equal to the name used in the requested URL.");
        return setSimpleVariable(restVariable, contentItem, false);
    }

    @DeleteMapping({"/content-service/content-items/{contentItemId}/metadata/{metadataName}"})
    public void deleteMetadataValue(@PathVariable("contentItemId") String contentItemId, @PathVariable("metadataName") String metadataName, @RequestParam(value = "scope", required = false) String scope, HttpServletResponse response) {
        ContentItem contentItem = getContentItemFromRequest(contentItemId);
        MetadataInstance metadataInstance = this.metadataService.getMetadataInstance(contentItem.getId(), metadataName);
        if (metadataInstance == null)
            throw new FlowableObjectNotFoundException("Could not find a metadata value with name '" + metadataName + "'.");
        this.metadataService.removeMetadataValue(contentItemId, metadataName);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
