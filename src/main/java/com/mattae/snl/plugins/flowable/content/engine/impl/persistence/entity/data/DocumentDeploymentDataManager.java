package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data;

import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentDeploymentEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.repository.DocumentDeploymentQueryImpl;
import org.flowable.common.engine.impl.persistence.entity.data.DataManager;

import java.util.List;
import java.util.Map;

public interface DocumentDeploymentDataManager extends DataManager<DocumentDeploymentEntity> {
    long findDeploymentCountByQueryCriteria(DocumentDeploymentQueryImpl paramDocumentDeploymentQueryImpl);

    List<DocumentDeployment> findDeploymentsByQueryCriteria(DocumentDeploymentQueryImpl paramDocumentDeploymentQueryImpl);

    List<String> getDeploymentResourceNames(String paramString);

    List<DocumentDeployment> findDeploymentsByNativeQuery(Map<String, Object> paramMap);

    long findDeploymentCountByNativeQuery(Map<String, Object> paramMap);
}
