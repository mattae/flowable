package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.impl;

import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentDeploymentEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentDeploymentEntityImpl;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.DocumentDeploymentDataManager;
import com.mattae.snl.plugins.flowable.content.engine.impl.repository.DocumentDeploymentQueryImpl;
import org.flowable.content.engine.ContentEngineConfiguration;
import org.flowable.content.engine.impl.persistence.entity.data.AbstractContentDataManager;

import java.util.List;
import java.util.Map;

public class MybatisDocumentDeploymentDataManager extends AbstractContentDataManager<DocumentDeploymentEntity> implements DocumentDeploymentDataManager {
    public MybatisDocumentDeploymentDataManager(ContentEngineConfiguration contentEngineConfiguration) {
        super(contentEngineConfiguration);
    }

    public Class<? extends DocumentDeploymentEntity> getManagedEntityClass() {
        return DocumentDeploymentEntityImpl.class;
    }

    public DocumentDeploymentEntity create() {
        return new DocumentDeploymentEntityImpl();
    }

    public long findDeploymentCountByQueryCriteria(DocumentDeploymentQueryImpl deploymentQuery) {
        return ((Long) getDbSqlSession().selectOne("selectDocumentDeploymentCountByQueryCriteria", deploymentQuery)).longValue();
    }

    public List<DocumentDeployment> findDeploymentsByQueryCriteria(DocumentDeploymentQueryImpl deploymentQuery) {
        return getDbSqlSession().selectList("selectDocumentDeploymentsByQueryCriteria", deploymentQuery);
    }

    public List<String> getDeploymentResourceNames(String deploymentId) {
        return getDbSqlSession().getSqlSession().selectList("selectDocumentResourceNamesByDeploymentId", deploymentId);
    }

    public List<DocumentDeployment> findDeploymentsByNativeQuery(Map<String, Object> parameterMap) {
        return getDbSqlSession().selectListWithRawParameter("selectDocumentDeploymentByNativeQuery", parameterMap);
    }

    public long findDeploymentCountByNativeQuery(Map<String, Object> parameterMap) {
        return ((Long) getDbSqlSession().selectOne("selectDocumentDeploymentCountByNativeQuery", parameterMap)).longValue();
    }
}
