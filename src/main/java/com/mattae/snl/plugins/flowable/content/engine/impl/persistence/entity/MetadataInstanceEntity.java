package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import com.mattae.snl.plugins.flowable.content.api.MetadataInstance;
import org.flowable.common.engine.impl.db.HasRevision;
import org.flowable.common.engine.impl.persistence.entity.ByteArrayRef;
import org.flowable.common.engine.impl.persistence.entity.Entity;
import org.flowable.variable.api.types.VariableType;

public interface MetadataInstanceEntity extends MetadataInstance, Entity, HasRevision {
    void setId(String paramString);

    void setName(String paramString);

    void setContentItemId(String paramString);

    VariableType getType();

    void setType(VariableType paramVariableType);

    void setValue(Object paramObject);

    void setTypeName(String paramString);
}
