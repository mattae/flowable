package com.mattae.snl.plugins.flowable.services.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import io.swagger.annotations.ApiModelProperty;
import org.flowable.common.rest.util.DateToStringSerializer;

import java.util.Date;

public class DocumentDeploymentResponse {
    protected String id;

    protected String name;

    @JsonSerialize(using = DateToStringSerializer.class, as = Date.class)
    protected Date deploymentTime;

    protected String category;

    protected String parentDeploymentId;

    protected String url;

    protected String tenantId;

    public DocumentDeploymentResponse(DocumentDeployment deployment, String url) {
        setId(deployment.getId());
        setName(deployment.getName());
        setDeploymentTime(deployment.getDeploymentTime());
        setCategory(deployment.getCategory());
        setParentDeploymentId(deployment.getParentDeploymentId());
        setTenantId(deployment.getTenantId());
        setUrl(url);
    }

    @ApiModelProperty(example = "10")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ApiModelProperty(example = "flowable-examples.document")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ApiModelProperty(example = "2019-10-13T14:54:26.750+02:00")
    public Date getDeploymentTime() {
        return this.deploymentTime;
    }

    public void setDeploymentTime(Date deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

    @ApiModelProperty(example = "examples")
    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @ApiModelProperty(example = "12")
    public String getParentDeploymentId() {
        return this.parentDeploymentId;
    }

    public void setParentDeploymentId(String parentDeploymentId) {
        this.parentDeploymentId = parentDeploymentId;
    }

    @ApiModelProperty(example = "http://localhost:8081/flowable-rest/content-api/content-repository/deployments/10")
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ApiModelProperty(example = "")
    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
