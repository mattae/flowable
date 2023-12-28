package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.impl;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentDefinitionEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentDefinitionEntityImpl;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.DocumentDefinitionDataManager;
import com.mattae.snl.plugins.flowable.content.engine.impl.repository.DocumentDefinitionQueryImpl;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.db.ListQueryParameterObject;
import org.flowable.content.engine.ContentEngineConfiguration;
import org.flowable.content.engine.impl.persistence.entity.data.AbstractContentDataManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisDocumentDefinitionDataManager extends AbstractContentDataManager<DocumentDefinitionEntity> implements DocumentDefinitionDataManager {
    public MybatisDocumentDefinitionDataManager(ContentEngineConfiguration contentEngineConfiguration) {
        super(contentEngineConfiguration);
    }

    public Class<? extends DocumentDefinitionEntity> getManagedEntityClass() {
        return (Class) DocumentDefinitionEntityImpl.class;
    }

    public DocumentDefinitionEntity create() {
        return (DocumentDefinitionEntity) new DocumentDefinitionEntityImpl();
    }

    public DocumentDefinitionEntity findLatestDocumentDefinitionByKey(String documentDefinitionKey) {
        return (DocumentDefinitionEntity) getDbSqlSession().selectOne("selectLatestDocumentDefinitionByKey", documentDefinitionKey);
    }

    public DocumentDefinitionEntity findLatestDocumentDefinitionByKeyAndTenantId(String documentDefinitionKey, String tenantId) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("documentDefinitionKey", documentDefinitionKey);
        params.put("tenantId", tenantId);
        return (DocumentDefinitionEntity) getDbSqlSession().selectOne("selectLatestDocumentDefinitionByKeyAndTenantId", params);
    }

    public DocumentDefinitionEntity findDocumentDefinitionByDeploymentAndKey(String deploymentId, String documentDefinitionKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("deploymentId", deploymentId);
        parameters.put("documentDefinitionKey", documentDefinitionKey);
        return (DocumentDefinitionEntity) getDbSqlSession().selectOne("selectDocumentDefinitionByDeploymentAndKey", parameters);
    }

    public DocumentDefinitionEntity findDocumentDefinitionByDeploymentAndKeyAndTenantId(String deploymentId, String documentDefinitionKey, String tenantId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("deploymentId", deploymentId);
        parameters.put("documentDefinitionKey", documentDefinitionKey);
        parameters.put("tenantId", tenantId);
        return (DocumentDefinitionEntity) getDbSqlSession().selectOne("selectDocumentDefinitionByDeploymentAndKeyAndTenantId", parameters);
    }

    public List<DocumentDefinition> findByCriteria(DocumentDefinitionQueryImpl query) {
        setSafeInValueLists(query);
        return getDbSqlSession().selectList("selectDocumentDefinitionsByQueryCriteria", (ListQueryParameterObject) query);
    }

    public long countByCriteria(DocumentDefinitionQueryImpl query) {
        setSafeInValueLists(query);
        return ((Long) getDbSqlSession().selectOne("selectDocumentDefinitionCountByQueryCriteria", query)).longValue();
    }

    public DocumentDefinitionEntity findDocumentDefinitionByKeyAndVersion(String documentDefinitionKey, Integer documentVersion) {
        Map<String, Object> params = new HashMap<>();
        params.put("documentDefinitionKey", documentDefinitionKey);
        params.put("documentVersion", documentVersion);
        List<DocumentDefinitionEntity> results = getDbSqlSession().selectList("selectDocumentDefinitionsByKeyAndVersion", params);
        if (results.size() == 1)
            return results.get(0);
        if (results.size() > 1)
            throw new FlowableException("There are " + results.size() + " documents with key = '" + documentDefinitionKey + "' and version = '" + documentVersion + "'.");
        return null;
    }

    public DocumentDefinitionEntity findDocumentDefinitionByKeyAndVersionAndTenantId(String documentDefinitionKey, Integer documentVersion, String tenantId) {
        Map<String, Object> params = new HashMap<>();
        params.put("documentDefinitionKey", documentDefinitionKey);
        params.put("documentVersion", documentVersion);
        params.put("tenantId", tenantId);
        List<DocumentDefinitionEntity> results = getDbSqlSession().selectList("selectDocumentDefinitionsByKeyAndVersionAndTenantId", params);
        if (results.size() == 1)
            return results.get(0);
        if (results.size() > 1)
            throw new FlowableException("There are " + results.size() + " documents with key = '" + documentDefinitionKey + "' and version = '" + documentVersion + "'.");
        return null;
    }

    public void deleteDocumentDefinitionsByDeploymentId(String deploymentId) {
        getDbSqlSession().delete("deleteDocumentDefinitionsByDeploymentId", deploymentId, getManagedEntityClass());
    }

    protected void setSafeInValueLists(DocumentDefinitionQueryImpl documentDefinitionQuery) {
        if (documentDefinitionQuery.getAccessibleByGroups() != null)
            documentDefinitionQuery.setSafeAccessibleByGroups(createSafeInValuesList(documentDefinitionQuery.getAccessibleByGroups()));
    }
}
