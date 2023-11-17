package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.impl;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntity;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntityImpl;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.MetadataInstanceDataManager;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.impl.cachematcher.MetadataInstanceByContentItemIdAndNameMatcher;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.impl.cachematcher.MetadataInstanceByContentItemIdAndNamesMatcher;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.impl.cachematcher.MetadataInstanceByContentItemIdMatcher;
import org.flowable.common.engine.impl.db.DbSqlSession;
import org.flowable.common.engine.impl.db.SingleCachedEntityMatcher;
import org.flowable.common.engine.impl.persistence.cache.CachedEntityMatcher;
import org.flowable.content.engine.ContentEngineConfiguration;
import org.flowable.content.engine.impl.persistence.entity.data.AbstractContentDataManager;

import java.util.*;

public class MybatisMetadataInstanceDataManager extends AbstractContentDataManager<MetadataInstanceEntity> implements MetadataInstanceDataManager {
    protected CachedEntityMatcher<MetadataInstanceEntity> metadataInstanceByContentItemIdMatcher = new MetadataInstanceByContentItemIdMatcher();

    protected CachedEntityMatcher<MetadataInstanceEntity> metadataInstanceByContentItemIdAndNamesMatcher = new MetadataInstanceByContentItemIdAndNamesMatcher();

    protected SingleCachedEntityMatcher<MetadataInstanceEntity> metadataInstanceByContentItemIdAndNameMatcher = new MetadataInstanceByContentItemIdAndNameMatcher();

    public MybatisMetadataInstanceDataManager(ContentEngineConfiguration contentEngineConfiguration) {
        super(contentEngineConfiguration);
    }

    public Class<? extends MetadataInstanceEntity> getManagedEntityClass() {
        return MetadataInstanceEntityImpl.class;
    }

    public MetadataInstanceEntity create() {
        return new MetadataInstanceEntityImpl();
    }

    public List<MetadataInstanceEntity> findMetadataInstancesByContentItemId(String contentItemId) {
        return getList("selectMetadataInstancesByContentItemId", contentItemId, this.metadataInstanceByContentItemIdMatcher, true);
    }

    public List<MetadataInstanceEntity> findMetadataInstancesByContentItemIds(Set<String> contentItemIds) {
        return getDbSqlSession().selectList("selectMetadataInstancesByContentItemIds", contentItemIds);
    }

    public MetadataInstanceEntity findMetadataInstanceByContentItemIdAndName(String contentItemId, String variableName) {
        Map<String, String> params = new HashMap<>(2);
        params.put("contentItemId", contentItemId);
        params.put("name", variableName);
        return getEntity("selectMetadataInstanceByContentItemIdAndName", params, this.metadataInstanceByContentItemIdAndNameMatcher, true);
    }

    public List<MetadataInstanceEntity> findMetadataInstancesByContentItemIdAndNames(String contentItemId, Collection<String> names) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("contentItemId", contentItemId);
        params.put("names", names);
        return getList("selectMetadataInstancesByContentItemIdAndNames", params, this.metadataInstanceByContentItemIdAndNamesMatcher);
    }

    public void deleteMetadataInstancesByContentItemId(String contentItemId) {
        DbSqlSession dbSqlSession = getDbSqlSession();
        if (isEntityInserted(dbSqlSession, "contentItemId", contentItemId)) {
            deleteCachedEntities(dbSqlSession, this.metadataInstanceByContentItemIdMatcher, contentItemId);
        } else {
            getDbSqlSession().delete("deleteBytesForMetadataInstancesByContentItemId", contentItemId, getManagedEntityClass());
            bulkDelete("deleteMetadataInstancesByContentItemId", this.metadataInstanceByContentItemIdMatcher, contentItemId);
        }
    }
}
