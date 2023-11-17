package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.impl.cachematcher;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntity;
import org.flowable.common.engine.impl.persistence.cache.CachedEntityMatcherAdapter;

public class MetadataInstanceByContentItemIdMatcher extends CachedEntityMatcherAdapter<MetadataInstanceEntity> {
    public boolean isRetained(MetadataInstanceEntity metadataInstanceEntity, Object parameter) {
        return (metadataInstanceEntity.getContentItemId() != null && metadataInstanceEntity
            .getContentItemId().equals(parameter));
    }
}
