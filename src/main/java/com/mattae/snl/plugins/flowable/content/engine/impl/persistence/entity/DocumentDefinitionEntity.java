package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import com.mattae.snl.plugins.flowable.content.api.DocumentDefinition;
import org.flowable.common.engine.impl.db.HasRevision;
import org.flowable.common.engine.impl.persistence.entity.Entity;
import org.flowable.identitylink.service.impl.persistence.entity.IdentityLinkEntity;

import java.util.Date;
import java.util.List;

public interface DocumentDefinitionEntity extends DocumentDefinition, Entity, HasRevision {
    void setKey(String paramString);

    void setVersion(int paramInt);

    void setDeploymentId(String paramString);

    void setTenantId(String paramString);

    void setName(String paramString);

    void setResourceName(String paramString);

    void setCategory(String paramString);

    void setCreated(Date paramDate);

    void setLastModified(Date paramDate);

}
