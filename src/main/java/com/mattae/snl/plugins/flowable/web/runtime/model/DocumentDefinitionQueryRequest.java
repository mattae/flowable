package com.mattae.snl.plugins.flowable.web.runtime.model;

import org.flowable.common.rest.api.PaginateRequest;

public class DocumentDefinitionQueryRequest extends PaginateRequest {

    protected String id;


    protected String ids;


    protected String key;


    protected String keyLike;


    protected String keys;


    protected String name;


    protected String nameLike;


    protected String category;


    protected String categoryLike;


    protected Integer version;


    protected Boolean latest;


    protected String deploymentId;


    protected String tenantId;


    protected String tenantIdLike;


    protected String accessibleByUser;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIds() {
        return this.ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyLike() {
        return this.keyLike;
    }

    public void setKeyLike(String keyLike) {
        this.keyLike = keyLike;
    }

    public String getKeys() {
        return this.keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
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

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryLike() {
        return this.categoryLike;
    }

    public void setCategoryLike(String categoryLike) {
        this.categoryLike = categoryLike;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getLatest() {
        return this.latest;
    }

    public void setLatest(Boolean latest) {
        this.latest = latest;
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
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

    public String getAccessibleByUser() {
        return this.accessibleByUser;
    }

    public void setAccessibleByUser(String accessibleByUser) {
        this.accessibleByUser = accessibleByUser;
    }
}
