package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity;

import org.flowable.common.engine.impl.persistence.entity.EntityManager;

import java.util.List;

public interface DocumentResourceEntityManager extends EntityManager<DocumentResourceEntity> {
    List<DocumentResourceEntity> findResourcesByDeploymentId(String paramString);

    DocumentResourceEntity findResourceByDeploymentIdAndResourceName(String paramString1, String paramString2);

    void deleteResourcesByDeploymentId(String paramString);
}
