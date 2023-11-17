package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.impl;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentResourceEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentResourceEntityImpl;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.DocumentResourceDataManager;
import org.flowable.content.engine.ContentEngineConfiguration;
import org.flowable.content.engine.impl.persistence.entity.data.AbstractContentDataManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisDocumentResourceDataManager extends AbstractContentDataManager<DocumentResourceEntity> implements DocumentResourceDataManager {
    public MybatisDocumentResourceDataManager(ContentEngineConfiguration contentEngineConfiguration) {
        super(contentEngineConfiguration);
    }

    public Class<? extends DocumentResourceEntity> getManagedEntityClass() {
        return DocumentResourceEntityImpl.class;
    }

    public DocumentResourceEntity create() {
        return new DocumentResourceEntityImpl();
    }

    public void deleteResourcesByDeploymentId(String deploymentId) {
        getDbSqlSession().delete("deleteDocumentResourcesByDeploymentId", deploymentId, getManagedEntityClass());
    }

    public DocumentResourceEntity findResourceByDeploymentIdAndResourceName(String deploymentId, String resourceName) {
        Map<String, Object> params = new HashMap<>();
        params.put("deploymentId", deploymentId);
        params.put("resourceName", resourceName);
        return (DocumentResourceEntity) getDbSqlSession().selectOne("selectDocumentResourceByDeploymentIdAndResourceName", params);
    }

    public List<DocumentResourceEntity> findResourcesByDeploymentId(String deploymentId) {
        return getDbSqlSession().selectList("selectDocumentResourcesByDeploymentId", deploymentId);
    }
}
