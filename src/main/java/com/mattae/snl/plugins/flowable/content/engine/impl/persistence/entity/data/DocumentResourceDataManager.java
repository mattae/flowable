package com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.data;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.DocumentResourceEntity;
import org.flowable.common.engine.impl.persistence.entity.data.DataManager;

import java.util.List;

public interface DocumentResourceDataManager extends DataManager<DocumentResourceEntity> {
    void deleteResourcesByDeploymentId(String paramString);

    DocumentResourceEntity findResourceByDeploymentIdAndResourceName(String paramString1, String paramString2);

    List<DocumentResourceEntity> findResourcesByDeploymentId(String paramString);
}
