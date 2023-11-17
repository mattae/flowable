package com.mattae.snl.plugins.flowable.content.engine.impl.repository;

import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.api.DocumentDeploymentQuery;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.interceptor.CommandExecutor;
import org.flowable.common.engine.impl.query.AbstractQuery;

import java.io.Serializable;
import java.util.List;

public class DocumentDeploymentQueryImpl extends AbstractQuery<DocumentDeploymentQuery, DocumentDeployment> implements DocumentDeploymentQuery, Serializable {
    private static final long serialVersionUID = 1L;

    protected String deploymentId;

    protected List<String> deploymentIds;

    protected String name;

    protected String nameLike;

    protected String key;

    protected String category;

    protected String categoryNotEquals;

    protected String tenantId;

    protected String tenantIdLike;

    protected boolean withoutTenantId;

    protected String parentDeploymentId;

    protected String parentDeploymentIdLike;

    protected String documentDefinitionKey;

    protected String documentDefinitionKeyLike;

    protected boolean latest;

    public DocumentDeploymentQueryImpl() {
    }

    public DocumentDeploymentQueryImpl(CommandContext commandContext) {
        super(commandContext);
    }

    public DocumentDeploymentQueryImpl(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    public DocumentDeploymentQueryImpl deploymentId(String deploymentId) {
        if (deploymentId == null)
            throw new FlowableIllegalArgumentException("Deployment id is null");
        this.deploymentId = deploymentId;
        return this;
    }

    public DocumentDeploymentQueryImpl deploymentIds(List<String> deploymentIds) {
        if (deploymentIds == null)
            throw new FlowableIllegalArgumentException("Deployment ids is null");
        this.deploymentIds = deploymentIds;
        return this;
    }

    public DocumentDeploymentQueryImpl deploymentName(String deploymentName) {
        if (deploymentName == null)
            throw new FlowableIllegalArgumentException("deploymentName is null");
        this.name = deploymentName;
        return this;
    }

    public DocumentDeploymentQueryImpl deploymentNameLike(String nameLike) {
        if (nameLike == null)
            throw new FlowableIllegalArgumentException("deploymentNameLike is null");
        this.nameLike = nameLike;
        return this;
    }

    public DocumentDeploymentQueryImpl deploymentKey(String deploymentKey) {
        if (deploymentKey == null)
            throw new FlowableIllegalArgumentException("deploymentKey is null");
        this.key = deploymentKey;
        return this;
    }

    public DocumentDeploymentQueryImpl deploymentCategory(String deploymentCategory) {
        if (deploymentCategory == null)
            throw new FlowableIllegalArgumentException("deploymentCategory is null");
        this.category = deploymentCategory;
        return this;
    }

    public DocumentDeploymentQueryImpl deploymentCategoryNotEquals(String deploymentCategoryNotEquals) {
        if (deploymentCategoryNotEquals == null)
            throw new FlowableIllegalArgumentException("deploymentCategoryExclude is null");
        this.categoryNotEquals = deploymentCategoryNotEquals;
        return this;
    }

    public DocumentDeploymentQueryImpl parentDeploymentId(String parentDeploymentId) {
        if (parentDeploymentId == null)
            throw new FlowableIllegalArgumentException("parentDeploymentId is null");
        this.parentDeploymentId = parentDeploymentId;
        return this;
    }

    public DocumentDeploymentQueryImpl parentDeploymentIdLike(String parentDeploymentIdLike) {
        if (parentDeploymentIdLike == null)
            throw new FlowableIllegalArgumentException("parentDeploymentIdLike is null");
        this.parentDeploymentIdLike = parentDeploymentIdLike;
        return this;
    }

    public DocumentDeploymentQueryImpl deploymentWithoutTenantId() {
        this.withoutTenantId = true;
        return this;
    }

    public DocumentDeploymentQueryImpl deploymentTenantId(String tenantId) {
        if (tenantId == null)
            throw new FlowableIllegalArgumentException("deploymentTenantId is null");
        this.tenantId = tenantId;
        return this;
    }

    public DocumentDeploymentQueryImpl deploymentTenantIdLike(String tenantIdLike) {
        if (tenantIdLike == null)
            throw new FlowableIllegalArgumentException("deploymentTenantIdLike is null");
        this.tenantIdLike = tenantIdLike;
        return this;
    }

    public DocumentDeploymentQueryImpl documentDefinitionKey(String key) {
        if (key == null)
            throw new FlowableIllegalArgumentException("key is null");
        this.documentDefinitionKey = key;
        return this;
    }

    public DocumentDeploymentQueryImpl documentDefinitionKeyLike(String keyLike) {
        if (keyLike == null)
            throw new FlowableIllegalArgumentException("keyLike is null");
        this.documentDefinitionKeyLike = keyLike;
        return this;
    }

    public DocumentDeploymentQueryImpl latest() {
        if (this.key == null)
            throw new FlowableIllegalArgumentException("latest can only be used together with a deployment key");
        this.latest = true;
        return this;
    }

    public DocumentDeploymentQuery orderByDeploymentId() {
        return (DocumentDeploymentQuery) orderBy(DocumentDeploymentQueryProperty.DEPLOYMENT_ID);
    }

    public DocumentDeploymentQuery orderByDeploymentTime() {
        return (DocumentDeploymentQuery) orderBy(DocumentDeploymentQueryProperty.DEPLOY_TIME);
    }

    public DocumentDeploymentQuery orderByDeploymentName() {
        return (DocumentDeploymentQuery) orderBy(DocumentDeploymentQueryProperty.DEPLOYMENT_NAME);
    }

    public DocumentDeploymentQuery orderByTenantId() {
        return (DocumentDeploymentQuery) orderBy(DocumentDeploymentQueryProperty.DEPLOYMENT_TENANT_ID);
    }

    public long executeCount(CommandContext commandContext) {
        return CommandContextUtil.getDocumentDeploymentEntityManager(commandContext).findDeploymentCountByQueryCriteria(this);
    }

    public List<DocumentDeployment> executeList(CommandContext commandContext) {
        return CommandContextUtil.getDocumentDeploymentEntityManager(commandContext).findDeploymentsByQueryCriteria(this);
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public List<String> getDeploymentIds() {
        return this.deploymentIds;
    }

    public String getName() {
        return this.name;
    }

    public String getNameLike() {
        return this.nameLike;
    }

    public String getKey() {
        return this.key;
    }

    public String getCategory() {
        return this.category;
    }

    public String getCategoryNotEquals() {
        return this.categoryNotEquals;
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

    public String getDocumentDefinitionKey() {
        return this.documentDefinitionKey;
    }

    public String getDocumentDefinitionKeyLike() {
        return this.documentDefinitionKeyLike;
    }

    public String getParentDeploymentId() {
        return this.parentDeploymentId;
    }

    public String getParentDeploymentIdLike() {
        return this.parentDeploymentIdLike;
    }
}
