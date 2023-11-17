package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.api.DocumentDeploymentQuery;
import com.mattae.snl.plugins.flowable.content.config.ContentEngineConfiguration;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.DocumentDeploymentDataManager;
import com.mattae.snl.plugins.flowable.content.engine.impl.repository.DocumentDeploymentQueryImpl;
import org.flowable.common.engine.api.repository.EngineResource;
import org.flowable.common.engine.impl.persistence.entity.AbstractEngineEntityManager;

import java.util.List;
import java.util.Map;

public class DocumentDeploymentEntityManagerImpl extends AbstractEngineEntityManager<ContentEngineConfiguration, DocumentDeploymentEntity, DocumentDeploymentDataManager> implements DocumentDeploymentEntityManager {
    public DocumentDeploymentEntityManagerImpl(ContentEngineConfiguration contentEngineConfiguration, DocumentDeploymentDataManager deploymentDataManager) {
        super(contentEngineConfiguration, deploymentDataManager);
    }

    public void insert(DocumentDeploymentEntity deployment) {
        insert(deployment, true);
    }

    public void insert(DocumentDeploymentEntity deployment, boolean fireEvent) {
        super.insert(deployment, fireEvent);
        if (deployment.getResources() != null)
            for (EngineResource resource : deployment.getResources().values()) {
                resource.setDeploymentId(deployment.getId());
                this.engineConfiguration.getDocumentResourceEntityManager().insert((DocumentResourceEntity) resource);
            }
    }

    public void deleteDeployment(String deploymentId) {
        deleteDocumentDefinitionsForDeployment(deploymentId);
        this.engineConfiguration.getDocumentResourceEntityManager().deleteResourcesByDeploymentId(deploymentId);
        delete(findById(deploymentId));
    }

    protected void deleteDocumentDefinitionsForDeployment(String deploymentId) {
        this.engineConfiguration.getDocumentDefinitionEntityManager().deleteDocumentDefinitionsByDeploymentId(deploymentId);
    }

    protected DocumentDefinitionEntity findLatestDocumentDefinition(DocumentDefinition documentDefinition) {
        DocumentDefinitionEntity latestDocument = null;
        DocumentDefinitionEntityManager documentDefinitionEntityManager = this.engineConfiguration.getDocumentDefinitionEntityManager();
        if (documentDefinition.getTenantId() != null && !"".equals(documentDefinition.getTenantId())) {
            latestDocument = documentDefinitionEntityManager.findLatestDocumentDefinitionByKeyAndTenantId(documentDefinition.getKey(), documentDefinition.getTenantId());
        } else {
            latestDocument = documentDefinitionEntityManager.findLatestDocumentDefinitionByKey(documentDefinition.getKey());
        }
        return latestDocument;
    }

    public DocumentDeploymentQuery createDocumentDeploymentQuery() {
        return new DocumentDeploymentQueryImpl(this.engineConfiguration.getCommandExecutor());
    }

    public long findDeploymentCountByQueryCriteria(DocumentDeploymentQueryImpl deploymentQuery) {
        return this.dataManager.findDeploymentCountByQueryCriteria(deploymentQuery);
    }

    public List<DocumentDeployment> findDeploymentsByQueryCriteria(DocumentDeploymentQueryImpl deploymentQuery) {
        return this.dataManager.findDeploymentsByQueryCriteria(deploymentQuery);
    }

    public List<String> getDeploymentResourceNames(String deploymentId) {
        return this.dataManager.getDeploymentResourceNames(deploymentId);
    }

    public List<DocumentDeployment> findDeploymentsByNativeQuery(Map<String, Object> parameterMap) {
        return this.dataManager.findDeploymentsByNativeQuery(parameterMap);
    }

    public long findDeploymentCountByNativeQuery(Map<String, Object> parameterMap) {
        return this.dataManager.findDeploymentCountByNativeQuery(parameterMap);
    }
}
