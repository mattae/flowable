package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.DocumentResourceDataManager;
import org.flowable.common.engine.impl.persistence.entity.AbstractEngineEntityManager;
import org.flowable.content.engine.ContentEngineConfiguration;

import java.util.List;

public class DocumentResourceEntityManagerImpl extends AbstractEngineEntityManager<ContentEngineConfiguration, DocumentResourceEntity, DocumentResourceDataManager> implements DocumentResourceEntityManager {
    public DocumentResourceEntityManagerImpl(ContentEngineConfiguration contentEngineConfiguration, DocumentResourceDataManager resourceDataManager) {
        super(contentEngineConfiguration, resourceDataManager);
    }

    public void deleteResourcesByDeploymentId(String deploymentId) {
        this.dataManager.deleteResourcesByDeploymentId(deploymentId);
    }

    public DocumentResourceEntity findResourceByDeploymentIdAndResourceName(String deploymentId, String resourceName) {
        return this.dataManager.findResourceByDeploymentIdAndResourceName(deploymentId, resourceName);
    }

    public List<DocumentResourceEntity> findResourcesByDeploymentId(String deploymentId) {
        return this.dataManager.findResourcesByDeploymentId(deploymentId);
    }
}
