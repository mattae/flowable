package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentDefinitionEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.repository.DocumentDefinitionQueryImpl;
import org.flowable.common.engine.impl.persistence.entity.data.DataManager;

import java.util.List;

public interface DocumentDefinitionDataManager extends DataManager<DocumentDefinitionEntity> {
    DocumentDefinitionEntity findLatestDocumentDefinitionByKey(String paramString);

    DocumentDefinitionEntity findLatestDocumentDefinitionByKeyAndTenantId(String paramString1, String paramString2);

    List<DocumentDefinition> findByCriteria(DocumentDefinitionQueryImpl paramDocumentDefinitionQueryImpl);

    long countByCriteria(DocumentDefinitionQueryImpl paramDocumentDefinitionQueryImpl);

    DocumentDefinitionEntity findDocumentDefinitionByDeploymentAndKey(String paramString1, String paramString2);

    DocumentDefinitionEntity findDocumentDefinitionByDeploymentAndKeyAndTenantId(String paramString1, String paramString2, String paramString3);

    DocumentDefinitionEntity findDocumentDefinitionByKeyAndVersion(String paramString, Integer paramInteger);

    DocumentDefinitionEntity findDocumentDefinitionByKeyAndVersionAndTenantId(String paramString1, Integer paramInteger, String paramString2);

    void deleteDocumentDefinitionsByDeploymentId(String paramString);
}
