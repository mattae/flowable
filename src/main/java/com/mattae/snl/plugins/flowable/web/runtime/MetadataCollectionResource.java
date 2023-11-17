package com.mattae.snl.plugins.flowable.web.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattae.snl.plugins.flowable.content.api.MetadataService;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.flowable.content.api.ContentItem;
import org.flowable.content.api.ContentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MetadataCollectionResource extends BaseMetadataResource {
    public MetadataCollectionResource(ObjectMapper objectMapper,
                                      ContentService contentService,
                                      MetadataService metadataService,
                                      ContentRestApiInterceptor restApiInterceptor,
                                      ContentRestResponseFactory restResponseFactory) {
        super(objectMapper, contentService, metadataService, restApiInterceptor, restResponseFactory);
    }

    @GetMapping(value = {"/content-service/content-items/{contentItemId}/metadata"}, produces = {"application/json"})
    public Map<String, Object> getMetadataValues(@PathVariable String contentItemId, HttpServletRequest request) {
        ContentItem contentItem = getContentItemFromRequest(contentItemId);
        return this.metadataService.getMetadataValues(contentItem.getId());
    }

    @PutMapping(value = {"/content-service/content-items/{contentItemId}/metadata"}, produces = {"application/json"}, consumes = {"application/json", "multipart/form-data"})
    public Object createOrUpdateMetadata(@PathVariable String contentItemId, HttpServletRequest request, HttpServletResponse response) {
        ContentItem contentItem = getContentItemFromRequest(contentItemId);
        return createVariable(contentItem, request, response);
    }

    @PostMapping(value = {"/content-service/content-items/{contentItemId}/metadata"}, produces = {"application/json"}, consumes = {"application/json", "multipart/form-data", "text/plain"})
    public Object createMetadata(@PathVariable String contentItemId, HttpServletRequest request, HttpServletResponse response) {
        ContentItem contentItem = getContentItemFromRequest(contentItemId);
        return createVariable(contentItem, request, response);
    }

    @DeleteMapping({"/content-service/content-items/{contentItemId}/metadata"})
    public void deleteMetadata(@PathVariable String contentItemId, HttpServletResponse response) {
        ContentItem contentItem = getContentItemFromRequest(contentItemId);
        deleteAllVariables(contentItem, response);
    }
}
