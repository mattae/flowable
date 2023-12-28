package com.mattae.snl.plugins.flowable.content.deployer;


import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.*;
import com.mattae.snl.plugins.flowable.content.engine.impl.repository.DocumentDefinitionQueryImpl;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.api.repository.EngineDeployment;
import org.flowable.common.engine.impl.EngineDeployer;
import org.flowable.common.engine.impl.persistence.deploy.DeploymentCache;
import org.flowable.content.engine.ContentEngineConfiguration;

import java.util.List;
import java.util.Map;

public class DocumentDeploymentManager {
    protected DeploymentCache<DocumentDefinitionCacheEntry> documentDefinitionCache;

    protected List<EngineDeployer> deployers;

    protected ContentEngineConfiguration contentEngineConfiguration;

    protected DocumentDeploymentEntityManager deploymentEntityManager;

    protected DocumentResourceEntityManager resourceEntityManager;

    protected DocumentDefinitionEntityManager documentDefinitionEntityManager;

    public void deploy(EngineDeployment deployment) {
        deploy(deployment, null);
    }

    public void deploy(EngineDeployment deployment, Map<String, Object> deploymentSettings) {
        for (EngineDeployer deployer : this.deployers)
            deployer.deploy(deployment, deploymentSettings);
    }

    public DocumentDefinition findDeployedDocumentDefinitionById(String documentDefinitionId) {
        if (documentDefinitionId == null)
            throw new FlowableIllegalArgumentException("Invalid document definition id : null");
        DocumentDefinitionCacheEntry cacheEntry = this.documentDefinitionCache.get(documentDefinitionId);
        DocumentDefinitionEntity documentDefinitionEntity = (cacheEntry != null) ? cacheEntry.getDocumentDefinitionEntity() : null;
        if (documentDefinitionEntity == null) {
            DocumentDefinition documentDefinition = this.documentDefinitionEntityManager.findById(documentDefinitionId);
            if (documentDefinition == null)
                throw new FlowableObjectNotFoundException("no deployed document definition found with id '" + documentDefinitionId + "'", DocumentDefinition.class);
            documentDefinitionEntity = resolveDocumentDefinition(documentDefinition).getDocumentDefinitionEntity();
        }
        return documentDefinitionEntity;
    }

    public DocumentDefinition findDeployedLatestActionDefinitionByKey(String documentDefinitionKey) {
        DocumentDefinitionEntity documentDefinitionEntity = this.documentDefinitionEntityManager.findLatestDocumentDefinitionByKey(documentDefinitionKey);
        if (documentDefinitionEntity == null)
            throw new FlowableObjectNotFoundException("no document definitions deployed with key '" + documentDefinitionKey + "'", DocumentDefinition.class);
        documentDefinitionEntity = resolveDocumentDefinition(documentDefinitionEntity).getDocumentDefinitionEntity();
        return documentDefinitionEntity;
    }

    public DocumentDefinition findDeployedLatestActionDefinitionByKeyAndTenantId(String documentDefinitionKey, String tenantId) {
        DocumentDefinitionEntity documentDefinitionEntity = this.documentDefinitionEntityManager.findLatestDocumentDefinitionByKeyAndTenantId(documentDefinitionKey, tenantId);
        if (documentDefinitionEntity == null)
            throw new FlowableObjectNotFoundException("no document definitions deployed with key '" + documentDefinitionKey + "' for tenant identifier '" + tenantId + "'", DocumentDefinition.class);
        documentDefinitionEntity = resolveDocumentDefinition(documentDefinitionEntity).getDocumentDefinitionEntity();
        return documentDefinitionEntity;
    }

    public DocumentDefinition findDeployedActionDefinitionByKeyAndVersionAndTenantId(String documentDefinitionKey, Integer documentDefinitionVersion, String tenantId) {
        DocumentDefinitionEntity documentDefinitionEntity = this.documentDefinitionEntityManager.findDocumentDefinitionByKeyAndVersionAndTenantId(documentDefinitionKey, documentDefinitionVersion, tenantId);
        if (documentDefinitionEntity == null)
            throw new FlowableObjectNotFoundException("no document definitions deployed with key = '" + documentDefinitionKey + "' and version = '" + documentDefinitionVersion + "'", DocumentDefinition.class);
        documentDefinitionEntity = resolveDocumentDefinition(documentDefinitionEntity).getDocumentDefinitionEntity();
        return documentDefinitionEntity;
    }

    public DocumentDefinitionCacheEntry resolveDocumentDefinition(DocumentDefinition documentDefinition) {
        String documentDefinitionId = documentDefinition.getId();
        String deploymentId = documentDefinition.getDeploymentId();
        DocumentDefinitionCacheEntry cachedDocumentDefinition = this.documentDefinitionCache.get(documentDefinitionId);
        if (cachedDocumentDefinition == null) {
            DocumentDeploymentEntity deployment = this.deploymentEntityManager.findById(deploymentId);
            List<DocumentResourceEntity> resources = this.resourceEntityManager.findResourcesByDeploymentId(deploymentId);
            for (DocumentResourceEntity documentResourceEntity : resources)
                deployment.addResource(documentResourceEntity);
            deployment.setNew(false);
            deploy(deployment, null);
            cachedDocumentDefinition = this.documentDefinitionCache.get(documentDefinitionId);
            if (cachedDocumentDefinition == null)
                throw new FlowableException("deployment '" + deploymentId + "' didn't put document definition '" + documentDefinitionId + "' in the cache");
        }
        return cachedDocumentDefinition;
    }

    public void removeDeployment(String deploymentId) {
        removeDeployment(deploymentId, true);
    }

    public void removeDeployment(String deploymentId, boolean cascade) {
        DocumentDeploymentEntity deployment = this.deploymentEntityManager.findById(deploymentId);
        if (deployment == null)
            throw new FlowableObjectNotFoundException("Could not find a deployment with id '" + deploymentId + "'.", DocumentDeploymentEntity.class);
        for (DocumentDefinition documentDefinition : (new DocumentDefinitionQueryImpl()).deploymentId(deploymentId).list()) {
            this.documentDefinitionCache.remove(documentDefinition.getId());
            this.documentDefinitionEntityManager.delete((DocumentDefinitionEntity) documentDefinition);
        }
        this.deploymentEntityManager.delete(deployment, cascade);
    }

    public List<EngineDeployer> getDeployers() {
        return this.deployers;
    }

    public void setDeployers(List<EngineDeployer> deployers) {
        this.deployers = deployers;
    }

    public DeploymentCache<DocumentDefinitionCacheEntry> getDocumentDefinitionCache() {
        return this.documentDefinitionCache;
    }

    public void setDocumentDefinitionCache(DeploymentCache<DocumentDefinitionCacheEntry> documentDefinitionCache) {
        this.documentDefinitionCache = documentDefinitionCache;
    }

    public ContentEngineConfiguration getContentEngineConfiguration() {
        return this.contentEngineConfiguration;
    }

    public void setContentEngineConfiguration(ContentEngineConfiguration contentEngineConfiguration) {
        this.contentEngineConfiguration = contentEngineConfiguration;
    }

    public DocumentDeploymentEntityManager getDeploymentEntityManager() {
        return this.deploymentEntityManager;
    }

    public void setDeploymentEntityManager(DocumentDeploymentEntityManager deploymentEntityManager) {
        this.deploymentEntityManager = deploymentEntityManager;
    }

    public DocumentResourceEntityManager getResourceEntityManager() {
        return this.resourceEntityManager;
    }

    public void setResourceEntityManager(DocumentResourceEntityManager resourceEntityManager) {
        this.resourceEntityManager = resourceEntityManager;
    }

    public DocumentDefinitionEntityManager getDocumentDefinitionEntityManager() {
        return this.documentDefinitionEntityManager;
    }

    public void setDocumentDefinitionEntityManager(DocumentDefinitionEntityManager documentDefinitionEntityManager) {
        this.documentDefinitionEntityManager = documentDefinitionEntityManager;
    }
}
