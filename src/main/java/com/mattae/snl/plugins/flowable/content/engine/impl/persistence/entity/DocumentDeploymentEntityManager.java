package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.api.DocumentDeploymentQuery;
import com.mattae.snl.plugins.flowable.content.engine.impl.repository.DocumentDeploymentQueryImpl;
import org.flowable.common.engine.impl.persistence.entity.EntityManager;

import java.util.List;
import java.util.Map;

public interface DocumentDeploymentEntityManager extends EntityManager<DocumentDeploymentEntity> {
    List<DocumentDeployment> findDeploymentsByQueryCriteria(DocumentDeploymentQueryImpl paramDocumentDeploymentQueryImpl);

    List<String> getDeploymentResourceNames(String paramString);

    List<DocumentDeployment> findDeploymentsByNativeQuery(Map<String, Object> paramMap);

    long findDeploymentCountByNativeQuery(Map<String, Object> paramMap);

    long findDeploymentCountByQueryCriteria(DocumentDeploymentQueryImpl paramDocumentDeploymentQueryImpl);

    DocumentDeploymentQuery createDocumentDeploymentQuery();

    void deleteDeployment(String paramString);
}
