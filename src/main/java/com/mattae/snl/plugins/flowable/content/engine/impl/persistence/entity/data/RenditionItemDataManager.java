package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data;

import com.mattae.snl.plugins.flowable.content.api.RenditionItem;
import com.mattae.snl.plugins.flowable.content.engine.impl.RenditionItemQueryImpl;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.RenditionItemEntity;
import org.flowable.common.engine.impl.persistence.entity.data.DataManager;

import java.util.List;

public interface RenditionItemDataManager extends DataManager<RenditionItemEntity> {
    long findRenditionItemCountByQueryCriteria(RenditionItemQueryImpl paramRenditionItemQueryImpl);

    List<RenditionItem> findRenditionItemsByQueryCriteria(RenditionItemQueryImpl paramRenditionItemQueryImpl);

    void deleteRenditionItemsByTaskId(String paramString);

    void deleteRenditionItemsByProcessInstanceId(String paramString);

    void deleteRenditionItemsByScopeIdAndScopeType(String paramString1, String paramString2);
}
