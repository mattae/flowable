package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import org.flowable.common.engine.impl.persistence.entity.EntityManager;
import org.flowable.content.engine.impl.persistence.entity.ContentItemEntity;
import org.flowable.variable.api.types.VariableType;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MetadataInstanceEntityManager extends EntityManager<MetadataInstanceEntity> {
    MetadataInstanceEntity create(String paramString1, String paramString2, VariableType paramVariableType, Object paramObject);

    void insert(MetadataInstanceEntity paramMetadataInstanceEntity, ContentItemEntity paramContentItemEntity, boolean paramBoolean);

    List<MetadataInstanceEntity> findMetadataInstancesByContentItemId(String paramString);

    List<MetadataInstanceEntity> findMetadataInstancesByContentItemIds(Set<String> paramSet);

    MetadataInstanceEntity findMetadataInstanceByContentItemIdAndName(String paramString1, String paramString2);

    List<MetadataInstanceEntity> findMetadataInstancesByContentItemIdAndNames(String paramString, Collection<String> paramCollection);

    void deleteMetadataInstancesByContentItemId(String paramString);
}
