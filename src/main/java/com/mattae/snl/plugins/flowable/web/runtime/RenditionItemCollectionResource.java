package com.mattae.snl.plugins.flowable.web.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattae.snl.plugins.flowable.services.model.RenditionItemResponse;
import com.mattae.snl.plugins.flowable.web.runtime.model.RenditionItemQueryRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.flowable.common.rest.api.DataResponse;
import org.flowable.common.rest.api.RequestUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RenditionItemCollectionResource extends RenditionItemBaseResource {

    protected final ContentRestResponseFactory contentRestResponseFactory;

    protected final ObjectMapper objectMapper;

    @GetMapping(value = {"/renditions/rendition-items"}, produces = {"application/json"})
    public DataResponse<RenditionItemResponse> getRenditionItems(@RequestParam Map<String, String> requestParams, HttpServletRequest httpRequest) {
        RenditionItemQueryRequest request = new RenditionItemQueryRequest();
        if (requestParams.containsKey("id"))
            request.setId(requestParams.get("id"));
        if (requestParams.containsKey("contentItemId"))
            request.setContentItemId(requestParams.get("contentItemId"));
        if (requestParams.containsKey("contentItemIdLike"))
            request.setContentItemIdLike(requestParams.get("contentItemIdLike"));
        if (requestParams.containsKey("contentItemName"))
            request.setContentItemName(requestParams.get("contentItemName"));
        if (requestParams.containsKey("contentItemNameLike"))
            request.setContentItemNameLike(requestParams.get("contentItemNameLike"));
        if (requestParams.containsKey("name"))
            request.setName(requestParams.get("name"));
        if (requestParams.containsKey("nameLike"))
            request.setNameLike(requestParams.get("nameLike"));
        if (requestParams.containsKey("mimeType"))
            request.setMimeType(requestParams.get("mimeType"));
        if (requestParams.containsKey("mimeTypeLike"))
            request.setMimeTypeLike(requestParams.get("mimeTypeLike"));
        if (requestParams.containsKey("taskId"))
            request.setTaskId(requestParams.get("taskId"));
        if (requestParams.containsKey("taskIdLike"))
            request.setTaskIdLike(requestParams.get("taskIdLike"));
        if (requestParams.containsKey("processInstanceId"))
            request.setProcessInstanceId(requestParams.get("processInstanceId"));
        if (requestParams.containsKey("processInstanceIdLike"))
            request.setProcessInstanceIdLike(requestParams.get("processInstanceIdLike"));
        if (requestParams.containsKey("scopeId"))
            request.setScopeId(requestParams.get("scopeId"));
        if (requestParams.containsKey("scopeIdLike"))
            request.setScopeIdLike(requestParams.get("scopeIdLike"));
        if (requestParams.containsKey("scopeType"))
            request.setScopeType(requestParams.get("scopeType"));
        if (requestParams.containsKey("contentStoreId"))
            request.setContentStoreId(requestParams.get("contentStoreId"));
        if (requestParams.containsKey("contentStoreIdLike"))
            request.setContentStoreIdLike(requestParams.get("contentStoreIdLike"));
        if (requestParams.containsKey("contentStoreName"))
            request.setContentStoreName(requestParams.get("contentStoreName"));
        if (requestParams.containsKey("contentStoreNameLike"))
            request.setContentStoreNameLike(requestParams.get("contentStoreNameLike"));
        if (requestParams.containsKey("contentSize"))
            request.setContentSize(Long.valueOf(requestParams.get("contentSize")));
        if (requestParams.containsKey("minimumContentSize"))
            request.setMinimumContentSize(Long.valueOf(requestParams.get("minimumContentSize")));
        if (requestParams.containsKey("maximumContentSize"))
            request.setMaximumContentSize(Long.valueOf(requestParams.get("maximumContentSize")));
        if (requestParams.containsKey("contentAvailable"))
            request.setContentAvailable(Boolean.valueOf(requestParams.get("contentAvailable")));
        if (requestParams.containsKey("createdOn"))
            request.setCreatedOn(RequestUtil.getDate(requestParams, "createdOn"));
        if (requestParams.containsKey("createdBefore"))
            request.setCreatedBefore(RequestUtil.getDate(requestParams, "createdBefore"));
        if (requestParams.containsKey("createdAfter"))
            request.setCreatedAfter(RequestUtil.getDate(requestParams, "createdAfter"));
        if (requestParams.containsKey("lastModifiedOn"))
            request.setLastModifiedOn(RequestUtil.getDate(requestParams, "lastModifiedOn"));
        if (requestParams.containsKey("lastModifiedBefore"))
            request.setLastModifiedBefore(RequestUtil.getDate(requestParams, "lastModifiedBefore"));
        if (requestParams.containsKey("lastModifiedAfter"))
            request.setLastModifiedAfter(RequestUtil.getDate(requestParams, "lastModifiedAfter"));
        if (requestParams.containsKey("tenantId"))
            request.setTenantId(requestParams.get("tenantId"));
        if (requestParams.containsKey("tenantIdLike"))
            request.setTenantIdLike(requestParams.get("tenantIdLike"));
        if (requestParams.containsKey("withoutTenantId") && Boolean.valueOf(requestParams.get("withoutTenantId")).booleanValue())
            request.setWithoutTenantId(Boolean.TRUE);
        return getRenditionItemsFromQueryRequest(request, requestParams);
    }
}
