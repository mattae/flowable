package com.mattae.snl.plugins.flowable.content.engine.impl.repository;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.api.DocumentDefinitionQuery;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.interceptor.CommandExecutor;
import org.flowable.common.engine.impl.query.AbstractQuery;

import java.util.Collection;
import java.util.List;

public class DocumentDefinitionQueryImpl extends AbstractQuery<DocumentDefinitionQuery, DocumentDefinition> implements DocumentDefinitionQuery {
    private static final long serialVersionUID = 1L;

    protected String id;

    protected Collection<String> ids;

    protected String key;

    protected String keyLike;

    protected Collection<String> keys;

    protected String name;

    protected String nameLike;

    protected String nameLikeIgnoreCase;

    protected String category;

    protected String categoryLike;

    protected Integer version;

    protected boolean latest;

    protected String deploymentId;

    protected String accessibleByUser;

    protected Collection<String> accessibleByGroups;

    protected Collection<? extends Collection<String>> safeAccessibleByGroups;

    protected String tenantId;

    protected String tenantIdLike;

    public DocumentDefinitionQueryImpl() {
    }

    public DocumentDefinitionQueryImpl(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    public DocumentDefinitionQuery id(String id) {
        if (id == null)
            throw new FlowableIllegalArgumentException("Document definition id is null");
        this.id = id;
        return this;
    }

    public DocumentDefinitionQuery ids(Collection<String> ids) {
        if (ids == null)
            throw new FlowableIllegalArgumentException("Document definition ids is null");
        this.ids = ids;
        return this;
    }

    public DocumentDefinitionQuery key(String key) {
        if (key == null)
            throw new FlowableIllegalArgumentException("Document definition key is null");
        this.key = key;
        return this;
    }

    public DocumentDefinitionQuery keyLike(String keyLike) {
        if (keyLike == null)
            throw new FlowableIllegalArgumentException("Document definition keyLike is null");
        this.keyLike = keyLike;
        return this;
    }

    public DocumentDefinitionQuery keys(Collection<String> keys) {
        if (keys == null)
            throw new FlowableIllegalArgumentException("Document definition keys is null");
        this.keys = keys;
        return this;
    }

    public DocumentDefinitionQuery deploymentId(String deploymentId) {
        if (deploymentId == null)
            throw new FlowableIllegalArgumentException("Document definition deploymentId is null");
        this.deploymentId = deploymentId;
        return this;
    }

    public DocumentDefinitionQuery name(String name) {
        if (name == null)
            throw new FlowableIllegalArgumentException("Document definition name is null");
        this.name = name;
        return this;
    }

    public DocumentDefinitionQuery nameLike(String nameLike) {
        if (nameLike == null)
            throw new FlowableIllegalArgumentException("Document definition nameLike is null");
        this.nameLike = nameLike;
        return this;
    }

    public DocumentDefinitionQuery nameLikeIgnoreCase(String nameLikeIgnoreCase) {
        if (nameLikeIgnoreCase == null)
            throw new FlowableIllegalArgumentException("Document definition nameLikeIgnoreCase is null");
        this.nameLikeIgnoreCase = nameLikeIgnoreCase.toLowerCase();
        return this;
    }

    public DocumentDefinitionQuery category(String category) {
        if (category == null)
            throw new FlowableIllegalArgumentException("Document definition category is null");
        this.category = category;
        return this;
    }

    public DocumentDefinitionQuery categoryLike(String categoryLike) {
        if (categoryLike == null)
            throw new FlowableIllegalArgumentException("Document definition categoryLike is null");
        this.categoryLike = categoryLike;
        return this;
    }

    public DocumentDefinitionQuery version(Integer version) {
        if (version == null)
            throw new FlowableIllegalArgumentException("Document definition version is null");
        this.version = version;
        return this;
    }

    public DocumentDefinitionQuery latestVersion() {
        this.latest = true;
        return this;
    }

    public DocumentDefinitionQuery anyVersion() {
        this.latest = false;
        return this;
    }

    public DocumentDefinitionQuery accessibleByUserOrGroups(String userId, Collection<String> groupKeys) {
        if (userId == null)
            throw new FlowableIllegalArgumentException("userId for accessibleByUser is null");
        this.accessibleByUser = userId;
        this.accessibleByGroups = groupKeys;
        return this;
    }

    public DocumentDefinitionQuery tenantId(String tenantId) {
        if (tenantId == null)
            throw new FlowableIllegalArgumentException("Action definition tenant id is null");
        this.tenantId = tenantId;
        return this;
    }

    public DocumentDefinitionQuery tenantIdLike(String tenantIdLike) {
        if (tenantIdLike == null)
            throw new FlowableIllegalArgumentException("Document definition tenant id is null");
        this.tenantIdLike = tenantIdLike;
        return this;
    }

    public long executeCount(CommandContext commandContext) {
        return CommandContextUtil.getDocumentDefinitionEntityManager(commandContext).countByCriteria(this);
    }

    public List<DocumentDefinition> executeList(CommandContext commandContext) {
        return CommandContextUtil.getDocumentDefinitionEntityManager(commandContext).findByCriteria(this);
    }

    public String getId() {
        return this.id;
    }

    public Collection<String> getIds() {
        return this.ids;
    }

    public String getKey() {
        return this.key;
    }

    public String getKeyLike() {
        return this.keyLike;
    }

    public Collection<String> getKeys() {
        return this.keys;
    }

    public String getName() {
        return this.name;
    }

    public String getNameLike() {
        return this.nameLike;
    }

    public String getCategory() {
        return this.category;
    }

    public String getCategoryLike() {
        return this.categoryLike;
    }

    public Integer getVersion() {
        return this.version;
    }

    public boolean isLatest() {
        return this.latest;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public String getTenantIdLike() {
        return this.tenantIdLike;
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public String getNameLikeIgnoreCase() {
        return this.nameLikeIgnoreCase;
    }

    public String getAccessibleByUser() {
        return this.accessibleByUser;
    }

    public Collection<String> getAccessibleByGroups() {
        return this.accessibleByGroups;
    }

    public Collection<? extends Collection<String>> getSafeAccessibleByGroups() {
        return this.safeAccessibleByGroups;
    }

    public void setSafeAccessibleByGroups(Collection<? extends Collection<String>> safeAccessibleByGroups) {
        this.safeAccessibleByGroups = safeAccessibleByGroups;
    }
}
