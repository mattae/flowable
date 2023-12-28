package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.impl.cachematcher;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntity;
import org.flowable.common.engine.impl.persistence.cache.CachedEntityMatcherAdapter;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class MetadataInstanceByContentItemIdAndNamesMatcher extends CachedEntityMatcherAdapter<MetadataInstanceEntity> {
    public boolean isRetained(MetadataInstanceEntity metadataInstanceEntity, Object parameter) {
        Map<String, Object> params = (Map<String, Object>) parameter;
        if (!Objects.equals(metadataInstanceEntity.getContentItemId(), params.get("contentItemId")))
            return false;
        Collection<String> names = (Collection<String>) params.get("names");
        return (names != null && names.contains(metadataInstanceEntity.getName()));
    }
}
