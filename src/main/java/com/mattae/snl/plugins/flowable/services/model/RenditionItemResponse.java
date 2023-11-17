package com.mattae.snl.plugins.flowable.services.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mattae.snl.plugins.flowable.content.api.RenditionItem;
import org.flowable.common.rest.util.DateToStringSerializer;

import java.util.Date;
public class RenditionItemResponse {
    protected String id;
    protected String contentItemId;
    protected String name;
    protected String mimeType;
    protected String taskId;
    protected String processInstanceId;

    protected String contentStoreId;

    protected String contentStoreName;

    protected boolean contentAvailable;

    protected String contentItemName;

    protected String tenantId;

    @JsonSerialize(using = DateToStringSerializer.class, as = Date.class)
    protected Date created;

    @JsonSerialize(using = DateToStringSerializer.class, as = Date.class)
    protected Date lastModified;

    protected String url;

    public RenditionItemResponse(RenditionItem renditionItem, String url) {
        setId(renditionItem.getId());
        setContentItemId(renditionItem.getContentItemId());
        setName(renditionItem.getName());
        setMimeType(renditionItem.getMimeType());
        setTaskId(renditionItem.getTaskId());
        setProcessInstanceId(renditionItem.getProcessInstanceId());
        setContentStoreId(renditionItem.getContentStoreId());
        setContentStoreName(renditionItem.getContentStoreName());
        setContentAvailable(renditionItem.isContentAvailable());
        setTenantId(renditionItem.getTenantId());
        setCreated(renditionItem.getCreated());
        setLastModified(renditionItem.getLastModified());
        setUrl(url);
        setContentItemName(renditionItem.getContentItemName());
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentItemId() {
        return this.contentItemId;
    }

    public void setContentItemId(String contentItemId) {
        this.contentItemId = contentItemId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getContentStoreId() {
        return this.contentStoreId;
    }

    public void setContentStoreId(String contentStoreId) {
        this.contentStoreId = contentStoreId;
    }

    public String getContentStoreName() {
        return this.contentStoreName;
    }

    public void setContentStoreName(String contentStoreName) {
        this.contentStoreName = contentStoreName;
    }

    public boolean isContentAvailable() {
        return this.contentAvailable;
    }

    public void setContentAvailable(boolean contentAvailable) {
        this.contentAvailable = contentAvailable;
    }

    public String getContentItemName() {
        return this.contentItemName;
    }

    public void setContentItemName(String contentItemName) {
        this.contentItemName = contentItemName;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
