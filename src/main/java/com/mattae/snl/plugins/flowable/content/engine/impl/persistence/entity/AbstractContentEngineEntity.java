package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import org.flowable.common.engine.impl.persistence.entity.AbstractEntity;

public abstract class AbstractContentEngineEntity extends AbstractEntity {
    public String getIdPrefix() {
        return "CON-";
    }
}
