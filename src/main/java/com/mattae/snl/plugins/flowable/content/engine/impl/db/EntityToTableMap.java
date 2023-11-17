package com.mattae.snl.plugins.flowable.content.engine.impl.db;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.*;
import org.flowable.common.engine.impl.persistence.entity.Entity;
import org.flowable.content.engine.impl.persistence.entity.ContentItemEntity;

import java.util.HashMap;
import java.util.Map;

public class EntityToTableMap {
    public static final Map<Class<?>, String> apiTypeToTableNameMap = new HashMap<>();

    public static final Map<Class<? extends Entity>, String> entityToTableNameMap = new HashMap<>();

    static {
        entityToTableNameMap.put(ContentItemEntity.class, "ACT_CO_CONTENT_ITEM");
        entityToTableNameMap.put(RenditionItemEntity.class, "ACT_CO_RENDITION_ITEM");
        entityToTableNameMap.put(DocumentDeploymentEntity.class, "ACT_CO_DEPLOYMENT");
        entityToTableNameMap.put(DocumentResourceEntity.class, "ACT_CO_DEPLOYMENT_RESOURCE");
        entityToTableNameMap.put(DocumentDefinitionEntity.class, "ACT_CO_DEFINITION");
        entityToTableNameMap.put(MetadataInstanceEntity.class, "ACT_CO_METADATA");
    }

    public static String getTableName(Class<?> entityClass) {
        if (Entity.class.isAssignableFrom(entityClass))
            return entityToTableNameMap.get(entityClass);
        return apiTypeToTableNameMap.get(entityClass);
    }
}
