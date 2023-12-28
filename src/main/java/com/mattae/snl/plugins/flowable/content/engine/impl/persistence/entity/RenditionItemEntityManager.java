package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import com.mattae.snl.plugins.flowable.content.api.RenditionItem;
import com.mattae.snl.plugins.flowable.content.engine.impl.RenditionItemQueryImpl;
import org.flowable.common.engine.impl.persistence.entity.EntityManager;

import java.util.List;

public interface RenditionItemEntityManager extends EntityManager<RenditionItemEntity> {
    List<RenditionItem> findRenditionItemsByQueryCriteria(RenditionItemQueryImpl paramRenditionItemQueryImpl);

    long findRenditionItemCountByQueryCriteria(RenditionItemQueryImpl paramRenditionItemQueryImpl);

    void deleteRenditionItemsByTaskId(String paramString);

    void deleteRenditionItemsByProcessInstanceId(String paramString);

    void deleteRenditionItemsByScopeIdAndScopeType(String paramString1, String paramString2);
}
