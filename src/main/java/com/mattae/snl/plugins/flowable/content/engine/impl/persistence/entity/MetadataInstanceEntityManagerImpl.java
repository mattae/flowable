package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.MetadataInstanceDataManager;
import org.flowable.common.engine.impl.persistence.entity.AbstractEngineEntityManager;
import org.flowable.common.engine.impl.persistence.entity.ByteArrayRef;
import org.flowable.content.engine.ContentEngineConfiguration;
import org.flowable.content.engine.impl.persistence.entity.ContentItemEntity;
import org.flowable.variable.api.types.VariableType;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MetadataInstanceEntityManagerImpl extends AbstractEngineEntityManager<ContentEngineConfiguration, MetadataInstanceEntity, MetadataInstanceDataManager> implements MetadataInstanceEntityManager {
    public MetadataInstanceEntityManagerImpl(ContentEngineConfiguration contentEngineConfiguration, MetadataInstanceDataManager metadataInstanceDataManager) {
        super(contentEngineConfiguration, metadataInstanceDataManager);
    }

    public MetadataInstanceEntity create(String contentItemId, String name, VariableType type, Object value) {
        MetadataInstanceEntity metadataInstance = create();
        metadataInstance.setContentItemId(contentItemId);
        metadataInstance.setName(name);
        metadataInstance.setType(type);
        metadataInstance.setTypeName(type.getTypeName());
        metadataInstance.setValue(value);
        return metadataInstance;
    }

    public List<MetadataInstanceEntity> findMetadataInstancesByContentItemId(String contentItemId) {
        return this.dataManager.findMetadataInstancesByContentItemId(contentItemId);
    }

    public List<MetadataInstanceEntity> findMetadataInstancesByContentItemIds(Set<String> contentItemIds) {
        return this.dataManager.findMetadataInstancesByContentItemIds(contentItemIds);
    }

    public MetadataInstanceEntity findMetadataInstanceByContentItemIdAndName(String contentItemId, String variableName) {
        return this.dataManager.findMetadataInstanceByContentItemIdAndName(contentItemId, variableName);
    }

    public List<MetadataInstanceEntity> findMetadataInstancesByContentItemIdAndNames(String contentItemId, Collection<String> names) {
        return this.dataManager.findMetadataInstancesByContentItemIdAndNames(contentItemId, names);
    }

    public void insert(MetadataInstanceEntity entity, boolean fireEvent) {
        getDataManager().insert(entity);
    }

    public void insert(MetadataInstanceEntity entity, ContentItemEntity contentItem, boolean fireEvent) {
        getDataManager().insert(entity);
    }

    public MetadataInstanceEntity update(MetadataInstanceEntity entity, boolean fireEvent) {
        MetadataInstanceEntity updatedEntity = getDataManager().update(entity);
        return updatedEntity;
    }

    public void delete(MetadataInstanceEntity entity, boolean fireDeleteEvent) {
        getDataManager().delete(entity);
        entity.setDeleted(true);
    }

    public void deleteMetadataInstancesByContentItemId(String contentItemId) {
        this.dataManager.deleteMetadataInstancesByContentItemId(contentItemId);
    }
}
