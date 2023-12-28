package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.content.api.RenditionItem;
import com.mattae.snl.plugins.flowable.content.api.RenditionItemQuery;
import com.mattae.snl.plugins.flowable.content.api.RenditionService;
import com.mattae.snl.plugins.flowable.services.model.RenditionItemResponse;
import com.mattae.snl.plugins.flowable.web.runtime.model.ContentRestApiInterceptor;
import com.mattae.snl.plugins.flowable.web.runtime.model.RenditionItemQueryRequest;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.common.rest.api.DataResponse;
import org.flowable.common.rest.api.PaginateListUtil;
import org.flowable.common.rest.api.RequestUtil;
import org.flowable.content.engine.impl.ContentItemQueryProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RenditionItemBaseResource {
    private static HashMap<String, QueryProperty> properties = new HashMap<>();

    static {
        properties.put("created", ContentItemQueryProperty.CREATED_DATE);
        properties.put("tenantId", ContentItemQueryProperty.TENANT_ID);
    }

    @Autowired
    protected ContentRestResponseFactory restResponseFactory;
    @Autowired
    protected RenditionService renditionService;
    @Autowired(required = false)
    protected ContentRestApiInterceptor restApiInterceptor;
    @Value("${flowable.platform.rest.default-list-response-size:100}")
    protected Integer defaultListResponseSize;

    protected DataResponse<RenditionItemResponse> getRenditionItemsFromQueryRequest(RenditionItemQueryRequest request,
                                                                                    Map<String, String> requestParams) {
        RenditionItemQuery renditionItemQuery = this.renditionService.createRenditionItemQuery();
        if (request.getId() != null)
            renditionItemQuery.id(request.getId());
        if (request.getIds() != null)
            renditionItemQuery.ids(request.getIds());
        if (request.getContentItemId() != null)
            renditionItemQuery.contentItemId(request.getContentItemId());
        if (request.getContentItemIdLike() != null)
            renditionItemQuery.contentItemIdLike(request.getContentItemIdLike());
        if (request.getContentItemName() != null)
            renditionItemQuery.contentItemName(request.getContentItemName());
        if (request.getContentItemNameLike() != null)
            renditionItemQuery.contentItemNameLike(request.getContentItemNameLike());
        if (request.getName() != null)
            renditionItemQuery.name(request.getName());
        if (request.getNameLike() != null)
            renditionItemQuery.nameLike(request.getNameLike());
        if (request.getMimeType() != null)
            renditionItemQuery.mimeType(request.getMimeType());
        if (request.getMimeTypeLike() != null)
            renditionItemQuery.mimeTypeLike(request.getMimeTypeLike());
        if (request.getTaskId() != null)
            renditionItemQuery.taskId(request.getTaskId());
        if (request.getTaskIdLike() != null)
            renditionItemQuery.taskIdLike(request.getTaskIdLike());
        if (request.getProcessInstanceId() != null)
            renditionItemQuery.processInstanceId(request.getProcessInstanceId());
        if (request.getProcessInstanceIdLike() != null)
            renditionItemQuery.processInstanceIdLike(request.getProcessInstanceIdLike());
        if (request.getScopeId() != null)
            renditionItemQuery.scopeId(request.getScopeId());
        if (request.getScopeIdLike() != null)
            renditionItemQuery.scopeIdLike(request.getScopeIdLike());
        if (request.getScopeType() != null)
            renditionItemQuery.scopeType(request.getScopeType());
        if (request.getContentStoreId() != null)
            renditionItemQuery.contentStoreId(request.getContentStoreId());
        if (request.getContentStoreIdLike() != null)
            renditionItemQuery.contentStoreIdLike(request.getContentStoreIdLike());
        if (request.getContentStoreName() != null)
            renditionItemQuery.contentStoreName(request.getContentStoreName());
        if (request.getContentStoreNameLike() != null)
            renditionItemQuery.contentStoreNameLike(request.getContentStoreNameLike());
        if (request.getContentSize() != null)
            renditionItemQuery.contentSize(request.getContentSize());
        if (request.getMinimumContentSize() != null)
            renditionItemQuery.minContentSize(request.getMinimumContentSize());
        if (request.getMaximumContentSize() != null)
            renditionItemQuery.maxContentSize(request.getMaximumContentSize());
        if (request.getContentAvailable() != null)
            renditionItemQuery.contentAvailable(request.getContentAvailable());
        if (request.getCreatedOn() != null)
            renditionItemQuery.createdDate(request.getCreatedOn());
        if (request.getCreatedBefore() != null)
            renditionItemQuery.createdDateBefore(request.getCreatedBefore());
        if (request.getCreatedAfter() != null)
            renditionItemQuery.createdDateAfter(request.getCreatedAfter());
        if (request.getLastModifiedOn() != null)
            renditionItemQuery.lastModifiedDate(request.getLastModifiedOn());
        if (request.getLastModifiedBefore() != null)
            renditionItemQuery.lastModifiedDateBefore(request.getLastModifiedBefore());
        if (request.getLastModifiedAfter() != null)
            renditionItemQuery.lastModifiedDateAfter(request.getLastModifiedAfter());
        if (request.getTenantId() != null)
            renditionItemQuery.tenantId(request.getTenantId());
        if (request.getTenantIdLike() != null)
            renditionItemQuery.tenantIdLike(request.getTenantIdLike());
        if (Boolean.TRUE.equals(request.getWithoutTenantId()))
            renditionItemQuery.withoutTenantId();
        if (this.restApiInterceptor != null)
            this.restApiInterceptor.accessRenditionItemInfoWithQuery(renditionItemQuery, request);
        if (request.getSize() == null || request.getSize() <= 0)
            request.setSize(RequestUtil.getInteger(requestParams, "size", this.defaultListResponseSize.intValue()));
        Objects.requireNonNull(this.restResponseFactory);
        return PaginateListUtil.paginateList(requestParams, request, renditionItemQuery, "created", properties, this.restResponseFactory::createRenditionItemResponseList);
    }

    protected RenditionItem getRenditionItemFromRequest(String renditionItemId) {
        return getRenditionItem(renditionItemId, this.renditionService.createRenditionItemQuery());
    }

    protected RenditionItem getRenditionItem(String renditionItemId, RenditionItemQuery renditionItemQuery) {
        RenditionItem renditionItem = (RenditionItem) renditionItemQuery.id(renditionItemId).singleResult();
        if (renditionItem == null)
            throw new FlowableObjectNotFoundException("Could not find a rendition item with id '" + renditionItemId + "'.", RenditionItem.class);
        if (this.restApiInterceptor != null)
            this.restApiInterceptor.accessRenditionItemInfoById(renditionItem);
        return renditionItem;
    }
}
