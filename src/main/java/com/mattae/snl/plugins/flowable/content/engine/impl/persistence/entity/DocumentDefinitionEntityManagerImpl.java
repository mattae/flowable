package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.api.DocumentDefinitionQuery;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.DocumentDefinitionDataManager;
import com.mattae.snl.plugins.flowable.content.engine.impl.repository.DocumentDefinitionQueryImpl;
import org.flowable.common.engine.impl.persistence.entity.AbstractEngineEntityManager;
import org.flowable.content.engine.ContentEngineConfiguration;

import java.util.List;

public class DocumentDefinitionEntityManagerImpl extends AbstractEngineEntityManager<ContentEngineConfiguration, DocumentDefinitionEntity, DocumentDefinitionDataManager> implements DocumentDefinitionEntityManager {
    public DocumentDefinitionEntityManagerImpl(ContentEngineConfiguration configuration, DocumentDefinitionDataManager documentDefinitionDataManager) {
        super(configuration, documentDefinitionDataManager);
    }

    public DocumentDefinitionEntity findLatestDocumentDefinitionByKey(String documentDefinitionKey) {
        return this.dataManager.findLatestDocumentDefinitionByKey(documentDefinitionKey);
    }

    public DocumentDefinitionEntity findLatestDocumentDefinitionByKeyAndTenantId(String documentDefinitionKey, String tenantId) {
        return this.dataManager.findLatestDocumentDefinitionByKeyAndTenantId(documentDefinitionKey, tenantId);
    }

    public DocumentDefinitionEntity findDocumentDefinitionByDeploymentAndKey(String deploymentId, String documentDefinitionKey) {
        return this.dataManager.findDocumentDefinitionByDeploymentAndKey(deploymentId, documentDefinitionKey);
    }

    public DocumentDefinitionEntity findDocumentDefinitionByDeploymentAndKeyAndTenantId(String deploymentId, String documentDefinitionKey, String tenantId) {
        return this.dataManager.findDocumentDefinitionByDeploymentAndKeyAndTenantId(deploymentId, documentDefinitionKey, tenantId);
    }

    public DocumentDefinitionQuery createDocumentDefinitionQuery() {
        return new DocumentDefinitionQueryImpl(this.engineConfiguration.getCommandExecutor());
    }

    public List<DocumentDefinition> findByCriteria(DocumentDefinitionQuery query) {
        return this.dataManager.findByCriteria((DocumentDefinitionQueryImpl) query);
    }

    public long countByCriteria(DocumentDefinitionQuery query) {
        return this.dataManager.countByCriteria((DocumentDefinitionQueryImpl) query);
    }

    public DocumentDefinitionEntity findDocumentDefinitionByKeyAndVersion(String documentDefinitionKey, Integer documentVersion) {
        return this.dataManager.findDocumentDefinitionByKeyAndVersion(documentDefinitionKey, documentVersion);
    }

    public DocumentDefinitionEntity findDocumentDefinitionByKeyAndVersionAndTenantId(String documentDefinitionKey, Integer documentVersion, String tenantId) {
        return this.dataManager.findDocumentDefinitionByKeyAndVersionAndTenantId(documentDefinitionKey, documentVersion, tenantId);
    }

    public void deleteDocumentDefinitionsByDeploymentId(String deploymentId) {
        this.dataManager.deleteDocumentDefinitionsByDeploymentId(deploymentId);
    }
}
