package com.mattae.snl.plugins.flowable.web.runtime.model;

import org.flowable.common.rest.api.PaginateRequest;

import java.util.Date;
import java.util.Set;

public class RenditionItemQueryRequest extends PaginateRequest {
    protected String id;

    protected Set<String> ids;

    protected String contentItemId;

    protected String contentItemIdLike;

    protected String contentItemName;

    protected String contentItemNameLike;

    protected String name;

    protected String nameLike;

    protected String mimeType;

    protected String mimeTypeLike;

    protected String taskId;

    protected String taskIdLike;

    protected String processInstanceId;

    protected String processInstanceIdLike;

    protected String scopeId;

    protected String scopeIdLike;

    protected String scopeType;

    protected String contentStoreId;

    protected String contentStoreIdLike;

    protected String contentStoreName;

    protected String contentStoreNameLike;

    protected Long contentSize;

    protected Long minimumContentSize;

    protected Long maximumContentSize;

    protected Boolean contentAvailable;

    protected Date createdOn;

    protected Date createdBefore;

    protected Date createdAfter;

    protected Date lastModifiedOn;

    protected Date lastModifiedBefore;

    protected Date lastModifiedAfter;

    protected String tenantId;

    protected String tenantIdLike;

    protected Boolean withoutTenantId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getIds() {
        return this.ids;
    }

    public void setIds(Set<String> ids) {
        this.ids = ids;
    }

    public String getContentItemId() {
        return this.contentItemId;
    }

    public void setContentItemId(String contentItemId) {
        this.contentItemId = contentItemId;
    }

    public String getContentItemIdLike() {
        return this.contentItemIdLike;
    }

    public void setContentItemIdLike(String contentItemIdLike) {
        this.contentItemIdLike = contentItemIdLike;
    }

    public String getContentItemName() {
        return this.contentItemName;
    }

    public void setContentItemName(String contentItemName) {
        this.contentItemName = contentItemName;
    }

    public String getContentItemNameLike() {
        return this.contentItemNameLike;
    }

    public void setContentItemNameLike(String contentItemNameLike) {
        this.contentItemNameLike = contentItemNameLike;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameLike() {
        return this.nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeTypeLike() {
        return this.mimeTypeLike;
    }

    public void setMimeTypeLike(String mimeTypeLike) {
        this.mimeTypeLike = mimeTypeLike;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskIdLike() {
        return this.taskIdLike;
    }

    public void setTaskIdLike(String taskIdLike) {
        this.taskIdLike = taskIdLike;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceIdLike() {
        return this.processInstanceIdLike;
    }

    public void setProcessInstanceIdLike(String processInstanceIdLike) {
        this.processInstanceIdLike = processInstanceIdLike;
    }

    public String getScopeId() {
        return this.scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    public String getScopeIdLike() {
        return this.scopeIdLike;
    }

    public void setScopeIdLike(String scopeIdLike) {
        this.scopeIdLike = scopeIdLike;
    }

    public String getScopeType() {
        return this.scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
    }

    public String getContentStoreId() {
        return this.contentStoreId;
    }

    public void setContentStoreId(String contentStoreId) {
        this.contentStoreId = contentStoreId;
    }

    public String getContentStoreIdLike() {
        return this.contentStoreIdLike;
    }

    public void setContentStoreIdLike(String contentStoreIdLike) {
        this.contentStoreIdLike = contentStoreIdLike;
    }

    public String getContentStoreName() {
        return this.contentStoreName;
    }

    public void setContentStoreName(String contentStoreName) {
        this.contentStoreName = contentStoreName;
    }

    public String getContentStoreNameLike() {
        return this.contentStoreNameLike;
    }

    public void setContentStoreNameLike(String contentStoreNameLike) {
        this.contentStoreNameLike = contentStoreNameLike;
    }

    public Long getContentSize() {
        return this.contentSize;
    }

    public void setContentSize(Long contentSize) {
        this.contentSize = contentSize;
    }

    public Long getMinimumContentSize() {
        return this.minimumContentSize;
    }

    public void setMinimumContentSize(Long minimumContentSize) {
        this.minimumContentSize = minimumContentSize;
    }

    public Long getMaximumContentSize() {
        return this.maximumContentSize;
    }

    public void setMaximumContentSize(Long maximumContentSize) {
        this.maximumContentSize = maximumContentSize;
    }

    public Boolean getContentAvailable() {
        return this.contentAvailable;
    }

    public void setContentAvailable(Boolean contentAvailable) {
        this.contentAvailable = contentAvailable;
    }

    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getCreatedBefore() {
        return this.createdBefore;
    }

    public void setCreatedBefore(Date createdBefore) {
        this.createdBefore = createdBefore;
    }

    public Date getCreatedAfter() {
        return this.createdAfter;
    }

    public void setCreatedAfter(Date createdAfter) {
        this.createdAfter = createdAfter;
    }

    public Date getLastModifiedOn() {
        return this.lastModifiedOn;
    }

    public void setLastModifiedOn(Date lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public Date getLastModifiedBefore() {
        return this.lastModifiedBefore;
    }

    public void setLastModifiedBefore(Date lastModifiedBefore) {
        this.lastModifiedBefore = lastModifiedBefore;
    }

    public Date getLastModifiedAfter() {
        return this.lastModifiedAfter;
    }

    public void setLastModifiedAfter(Date lastModifiedAfter) {
        this.lastModifiedAfter = lastModifiedAfter;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantIdLike() {
        return this.tenantIdLike;
    }

    public void setTenantIdLike(String tenantIdLike) {
        this.tenantIdLike = tenantIdLike;
    }

    public Boolean getWithoutTenantId() {
        return this.withoutTenantId;
    }

    public void setWithoutTenantId(Boolean withoutTenantId) {
        this.withoutTenantId = withoutTenantId;
    }
}
