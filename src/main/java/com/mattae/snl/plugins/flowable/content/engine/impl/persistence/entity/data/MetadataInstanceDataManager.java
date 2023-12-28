package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntity;
import org.flowable.common.engine.impl.persistence.entity.data.DataManager;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MetadataInstanceDataManager extends DataManager<MetadataInstanceEntity> {
    List<MetadataInstanceEntity> findMetadataInstancesByContentItemId(String paramString);

    List<MetadataInstanceEntity> findMetadataInstancesByContentItemIds(Set<String> paramSet);

    MetadataInstanceEntity findMetadataInstanceByContentItemIdAndName(String paramString1, String paramString2);

    List<MetadataInstanceEntity> findMetadataInstancesByContentItemIdAndNames(String paramString, Collection<String> paramCollection);

    void deleteMetadataInstancesByContentItemId(String paramString);
}
