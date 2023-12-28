package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import com.mattae.snl.plugins.flowable.content.api.DocumentDeployment;
import org.flowable.common.engine.api.repository.EngineResource;
import org.flowable.common.engine.impl.persistence.entity.Entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DocumentDeploymentEntity extends DocumentDeployment, Entity {
    void addResource(DocumentResourceEntity paramDocumentResourceEntity);

    void addDeployedArtifact(Object paramObject);

    <T> List<T> getDeployedArtifacts(Class<T> paramClass);

    void setName(String paramString);

    void setKey(String paramString);

    void setCategory(String paramString);

    void setTenantId(String paramString);

    void setParentDeploymentId(String paramString);

    void setResources(Map<String, EngineResource> paramMap);

    void setDeploymentTime(Date paramDate);

    boolean isNew();

    void setNew(boolean paramBoolean);
}
