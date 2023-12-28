package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import com.mattae.snl.plugins.flowable.content.api.RenditionItem;
import com.mattae.snl.plugins.flowable.content.engine.impl.RenditionItemQueryImpl;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.RenditionItemDataManager;
import org.flowable.common.engine.impl.persistence.entity.AbstractEngineEntityManager;
import org.flowable.content.engine.ContentEngineConfiguration;

import java.util.List;

public class RenditionItemEntityManagerImpl extends AbstractEngineEntityManager<ContentEngineConfiguration, RenditionItemEntity, RenditionItemDataManager> implements RenditionItemEntityManager {
    public RenditionItemEntityManagerImpl(ContentEngineConfiguration contentEngineConfiguration, RenditionItemDataManager renditionItemDataManager) {
        super(contentEngineConfiguration, renditionItemDataManager);
    }

    public long findRenditionItemCountByQueryCriteria(RenditionItemQueryImpl renditionItemQuery) {
        return ((RenditionItemDataManager) this.dataManager).findRenditionItemCountByQueryCriteria(renditionItemQuery);
    }

    public List<RenditionItem> findRenditionItemsByQueryCriteria(RenditionItemQueryImpl renditionItemQuery) {
        return ((RenditionItemDataManager) this.dataManager).findRenditionItemsByQueryCriteria(renditionItemQuery);
    }

    public void deleteRenditionItemsByTaskId(String taskId) {
        ((RenditionItemDataManager) this.dataManager).deleteRenditionItemsByTaskId(taskId);
    }

    public void deleteRenditionItemsByProcessInstanceId(String processInstanceId) {
        ((RenditionItemDataManager) this.dataManager).deleteRenditionItemsByProcessInstanceId(processInstanceId);
    }

    public void deleteRenditionItemsByScopeIdAndScopeType(String scopeId, String scopeType) {
        ((RenditionItemDataManager) this.dataManager).deleteRenditionItemsByScopeIdAndScopeType(scopeId, scopeType);
    }
}
