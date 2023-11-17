package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.impl;

import com.mattae.snl.plugins.flowable.content.api.RenditionItem;
import com.mattae.snl.plugins.flowable.content.engine.impl.RenditionItemQueryImpl;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.RenditionItemEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.RenditionItemEntityImpl;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.RenditionItemDataManager;
import org.flowable.content.engine.ContentEngineConfiguration;
import org.flowable.content.engine.impl.persistence.entity.data.AbstractContentDataManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisRenditionItemDataManager extends AbstractContentDataManager<RenditionItemEntity> implements RenditionItemDataManager {
    public MybatisRenditionItemDataManager(ContentEngineConfiguration contentEngineConfiguration) {
        super(contentEngineConfiguration);
    }

    public Class<? extends RenditionItemEntity> getManagedEntityClass() {
        return RenditionItemEntityImpl.class;
    }

    public RenditionItemEntity create() {
        return new RenditionItemEntityImpl();
    }

    public long findRenditionItemCountByQueryCriteria(RenditionItemQueryImpl renditionItemQuery) {
        return ((Long) getDbSqlSession().selectOne("selectRenditionItemCountByQueryCriteria", renditionItemQuery)).longValue();
    }

    public List<RenditionItem> findRenditionItemsByQueryCriteria(RenditionItemQueryImpl renditionItemQuery) {
        return getDbSqlSession().selectList("selectRenditionItemsByQueryCriteria", renditionItemQuery, getManagedEntityClass());
    }

    public void deleteRenditionItemsByTaskId(String taskId) {
        getDbSqlSession().delete("deleteRenditionItemsByTaskId", taskId, getManagedEntityClass());
    }

    public void deleteRenditionItemsByProcessInstanceId(String processInstanceId) {
        getDbSqlSession().delete("deleteRenditionItemsByProcessInstanceId", processInstanceId, getManagedEntityClass());
    }

    public void deleteRenditionItemsByScopeIdAndScopeType(String scopeId, String scopeType) {
        Map<String, String> params = new HashMap<>(2);
        params.put("scopeId", scopeId);
        params.put("scopeType", scopeType);
        getDbSqlSession().delete("deleteRenditionItemsByScopeIdAndScopeType", params, getManagedEntityClass());
    }
}
