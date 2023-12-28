package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.api.DocumentDefinitionQuery;
import org.flowable.common.engine.impl.persistence.entity.EntityManager;

import java.util.List;

public interface DocumentDefinitionEntityManager extends EntityManager<DocumentDefinitionEntity> {
    DocumentDefinitionEntity findLatestDocumentDefinitionByKey(String paramString);

    DocumentDefinitionEntity findLatestDocumentDefinitionByKeyAndTenantId(String paramString1, String paramString2);

    DocumentDefinitionEntity findDocumentDefinitionByDeploymentAndKey(String paramString1, String paramString2);

    DocumentDefinitionEntity findDocumentDefinitionByDeploymentAndKeyAndTenantId(String paramString1, String paramString2, String paramString3);

    DocumentDefinitionQuery createDocumentDefinitionQuery();

    List<DocumentDefinition> findByCriteria(DocumentDefinitionQuery paramDocumentDefinitionQuery);

    long countByCriteria(DocumentDefinitionQuery paramDocumentDefinitionQuery);

    DocumentDefinitionEntity findDocumentDefinitionByKeyAndVersion(String paramString, Integer paramInteger);

    DocumentDefinitionEntity findDocumentDefinitionByKeyAndVersionAndTenantId(String paramString1, Integer paramInteger, String paramString2);

    void deleteDocumentDefinitionsByDeploymentId(String paramString);
}
