package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import com.mattae.snl.plugins.flowable.content.api.RenditionItem;
import org.flowable.common.engine.impl.persistence.entity.Entity;

import java.util.Date;

public interface RenditionItemEntity extends RenditionItem, Entity {
    void setContentAvailable(boolean paramBoolean);

    void setContentSize(Long paramLong);

    void setCreated(Date paramDate);

    void setLastModified(Date paramDate);
}
