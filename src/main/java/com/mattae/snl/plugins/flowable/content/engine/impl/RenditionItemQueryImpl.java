package com.mattae.snl.plugins.flowable.content.engine.impl;

import com.mattae.snl.plugins.flowable.content.api.RenditionItem;
import com.mattae.snl.plugins.flowable.content.api.RenditionItemQuery;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.RenditionItemEntity;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.query.CacheAwareQuery;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.interceptor.CommandExecutor;
import org.flowable.common.engine.impl.query.AbstractQuery;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class RenditionItemQueryImpl extends AbstractQuery<RenditionItemQuery, RenditionItem> implements RenditionItemQuery, CacheAwareQuery<RenditionItemEntity>, Serializable {
    private static final long serialVersionUID = 1L;

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

    protected String scopeTypeLike;

    protected String contentStoreId;

    protected String contentStoreIdLike;

    protected String contentStoreName;

    protected String contentStoreNameLike;

    protected Boolean contentAvailable;

    protected Long contentSize;

    protected Long minContentSize;

    protected Long maxContentSize;

    protected Date createdDate;

    protected Date createdDateBefore;

    protected Date createdDateAfter;

    protected Date lastModifiedDate;

    protected Date lastModifiedDateBefore;

    protected Date lastModifiedDateAfter;

    protected String tenantId;

    protected String tenantIdLike;

    protected boolean withoutTenantId;

    protected String renditionType;

    public RenditionItemQueryImpl(CommandContext commandContext) {
        super(commandContext);
    }

    public RenditionItemQueryImpl(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    public RenditionItemQueryImpl id(String id) {
        this.id = id;
        return this;
    }

    public RenditionItemQueryImpl ids(Set<String> ids) {
        this.ids = ids;
        return this;
    }

    public RenditionItemQueryImpl contentItemId(String contentItemId) {
        this.contentItemId = contentItemId;
        return this;
    }

    public RenditionItemQueryImpl contentItemIdLike(String contentItemIdLike) {
        this.contentItemIdLike = contentItemIdLike;
        return this;
    }

    public RenditionItemQueryImpl contentItemName(String contentItemName) {
        this.contentItemName = contentItemName;
        return this;
    }

    public RenditionItemQueryImpl contentItemNameLike(String contentItemNameLike) {
        this.contentItemNameLike = contentItemNameLike;
        return this;
    }

    public RenditionItemQueryImpl name(String name) {
        this.name = name;
        return this;
    }

    public RenditionItemQueryImpl nameLike(String nameLike) {
        this.nameLike = nameLike;
        return this;
    }

    public RenditionItemQueryImpl mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public RenditionItemQueryImpl mimeTypeLike(String mimeTypeLike) {
        this.mimeTypeLike = mimeTypeLike;
        return this;
    }

    public RenditionItemQueryImpl taskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public RenditionItemQueryImpl taskIdLike(String taskIdLike) {
        this.taskIdLike = taskIdLike;
        return this;
    }

    public RenditionItemQueryImpl processInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public RenditionItemQueryImpl processInstanceIdLike(String processInstanceIdLike) {
        this.processInstanceIdLike = processInstanceIdLike;
        return this;
    }

    public RenditionItemQueryImpl scopeId(String scopeId) {
        this.scopeId = scopeId;
        return this;
    }

    public RenditionItemQueryImpl scopeIdLike(String scopeIdLike) {
        this.scopeIdLike = scopeIdLike;
        return this;
    }

    public RenditionItemQueryImpl scopeType(String scopeType) {
        this.scopeType = scopeType;
        return this;
    }

    public RenditionItemQueryImpl scopeTypeLike(String scopeTypeLike) {
        this.scopeTypeLike = scopeTypeLike;
        return this;
    }

    public RenditionItemQueryImpl contentStoreId(String contentStoreId) {
        this.contentStoreId = contentStoreId;
        return this;
    }

    public RenditionItemQueryImpl contentStoreIdLike(String contentStoreIdLike) {
        this.contentStoreIdLike = contentStoreIdLike;
        return this;
    }

    public RenditionItemQueryImpl contentStoreName(String contentStoreName) {
        this.contentStoreName = contentStoreName;
        return this;
    }

    public RenditionItemQueryImpl contentStoreNameLike(String contentStoreNameLike) {
        this.contentStoreNameLike = contentStoreNameLike;
        return this;
    }

    public RenditionItemQueryImpl contentAvailable(Boolean contentAvailable) {
        this.contentAvailable = contentAvailable;
        return this;
    }

    public RenditionItemQueryImpl contentSize(Long contentSize) {
        this.contentSize = contentSize;
        return this;
    }

    public RenditionItemQueryImpl minContentSize(Long minContentSize) {
        this.minContentSize = minContentSize;
        return this;
    }

    public RenditionItemQueryImpl maxContentSize(Long maxContentSize) {
        this.maxContentSize = maxContentSize;
        return this;
    }

    public RenditionItemQueryImpl createdDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public RenditionItemQueryImpl createdDateBefore(Date createdDateBefore) {
        this.createdDateBefore = createdDateBefore;
        return this;
    }

    public RenditionItemQueryImpl createdDateAfter(Date createdDateAfter) {
        this.createdDateAfter = createdDateAfter;
        return this;
    }

    public RenditionItemQueryImpl lastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public RenditionItemQueryImpl lastModifiedDateBefore(Date lastModifiedDateBefore) {
        this.lastModifiedDateBefore = lastModifiedDateBefore;
        return this;
    }

    public RenditionItemQueryImpl lastModifiedDateAfter(Date lastModifiedDateAfter) {
        this.lastModifiedDateAfter = lastModifiedDateAfter;
        return this;
    }

    public RenditionItemQueryImpl tenantId(String tenantId) {
        if (tenantId == null)
            throw new FlowableIllegalArgumentException("deploymentTenantId is null");
        this.tenantId = tenantId;
        return this;
    }

    public RenditionItemQueryImpl tenantIdLike(String tenantIdLike) {
        if (tenantIdLike == null)
            throw new FlowableIllegalArgumentException("deploymentTenantIdLike is null");
        this.tenantIdLike = tenantIdLike;
        return this;
    }

    public RenditionItemQueryImpl withoutTenantId() {
        this.withoutTenantId = true;
        return this;
    }

    public RenditionItemQueryImpl renditionType(String renditionType) {
        this.renditionType = renditionType;
        return this;
    }

    public RenditionItemQuery orderByCreatedDate() {
        return orderBy(RenditionItemQueryProperty.CREATED_DATE);
    }

    public RenditionItemQuery orderByTenantId() {
        return orderBy(RenditionItemQueryProperty.TENANT_ID);
    }

    public long executeCount(CommandContext commandContext) {
        return CommandContextUtil.getRenditionItemEntityManager().findRenditionItemCountByQueryCriteria(this);
    }

    public List<RenditionItem> executeList(CommandContext commandContext) {
        return CommandContextUtil.getRenditionItemEntityManager().findRenditionItemsByQueryCriteria(this);
    }

    public String getId() {
        return this.id;
    }

    public String getContentItemId() {
        return this.contentItemId;
    }

    public String getContentItemIdLike() {
        return this.contentItemIdLike;
    }

    public String getContentItemName() {
        return this.contentItemName;
    }

    public String getContentItemNameLike() {
        return this.contentItemNameLike;
    }

    public Set<String> getIds() {
        return this.ids;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getTaskIdLike() {
        return this.taskIdLike;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public String getProcessInstanceIdLike() {
        return this.processInstanceIdLike;
    }

    public String getScopeId() {
        return this.scopeId;
    }

    public String getScopeIdLike() {
        return this.scopeIdLike;
    }

    public String getScopeType() {
        return this.scopeType;
    }

    public String getScopeTypeLike() {
        return this.scopeTypeLike;
    }

    public String getContentStoreId() {
        return this.contentStoreId;
    }

    public String getContentStoreIdLike() {
        return this.contentStoreIdLike;
    }

    public String getContentStoreName() {
        return this.contentStoreName;
    }

    public String getContentStoreNameLike() {
        return this.contentStoreNameLike;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public String getMimeTypeLike() {
        return this.mimeTypeLike;
    }

    public Boolean getContentAvailable() {
        return this.contentAvailable;
    }

    public Long getContentSize() {
        return this.contentSize;
    }

    public Long getMinContentSize() {
        return this.minContentSize;
    }

    public Long getMaxContentSize() {
        return this.maxContentSize;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public Date getCreatedDateBefore() {
        return this.createdDateBefore;
    }

    public Date getCreatedDateAfter() {
        return this.createdDateAfter;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Date getLastModifiedDateBefore() {
        return this.lastModifiedDateBefore;
    }

    public Date getLastModifiedDateAfter() {
        return this.lastModifiedDateAfter;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public String getTenantIdLike() {
        return this.tenantIdLike;
    }

    public boolean isWithoutTenantId() {
        return this.withoutTenantId;
    }

    public String getRenditionType() {
        return this.renditionType;
    }
}
