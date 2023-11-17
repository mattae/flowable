package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data.impl.cachematcher;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.MetadataInstanceEntity;
import org.flowable.common.engine.impl.db.SingleCachedEntityMatcher;

import java.util.Map;
import java.util.Objects;

public class MetadataInstanceByContentItemIdAndNameMatcher implements SingleCachedEntityMatcher<MetadataInstanceEntity> {
    public boolean isRetained(MetadataInstanceEntity metadataInstanceEntity, Object parameter) {
        Map<String, String> params = (Map<String, String>) parameter;
        return (metadataInstanceEntity.getContentItemId() != null && metadataInstanceEntity
            .getContentItemId().equals(params.get("contentItemId")) &&
            Objects.equals(metadataInstanceEntity.getName(), params.get("name")));
    }
}
