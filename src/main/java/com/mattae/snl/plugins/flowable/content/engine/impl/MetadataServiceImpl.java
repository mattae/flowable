package com.mattae.snl.plugins.flowable.content.engine.impl;


import com.mattae.snl.plugins.flowable.content.api.MetadataInstance;
import com.mattae.snl.plugins.flowable.content.api.MetadataService;
import com.mattae.snl.plugins.flowable.content.engine.impl.cmd.*;
import org.flowable.common.engine.impl.service.CommonEngineServiceImpl;
import org.flowable.content.engine.ContentEngineConfiguration;

import java.util.Collection;
import java.util.Map;

public class MetadataServiceImpl extends CommonEngineServiceImpl<ContentEngineConfiguration> implements MetadataService {
    public Object getMetadataValue(String contentItemId, String metadataName) {
        return this.commandExecutor.execute(new GetMetadataValueCmd(contentItemId, metadataName));
    }

    public Map<String, Object> getMetadataValues(String contentItemId) {
        return this.commandExecutor.execute(new GetMetadataValuesCmd(contentItemId));
    }

    public MetadataInstance getMetadataInstance(String contentItemId, String metadataName) {
        return this.commandExecutor.execute(new GetMetadataInstanceCmd(contentItemId, metadataName));
    }

    public Map<String, MetadataInstance> getMetadataInstances(String contentItemId) {
        return this.commandExecutor.execute(new GetMetadataInstancesCmd(contentItemId, null));
    }

    public Map<String, MetadataInstance> getMetadataInstances(String contentItemId, Collection<String> metadataNames) {
        return this.commandExecutor.execute(new GetMetadataInstancesCmd(contentItemId, metadataNames));
    }

    public void setMetadataValue(String contentItemId, String metadataName, Object metadataValue) {
        this.commandExecutor.execute(new SetMetaDataValueCmd(contentItemId, metadataName, metadataValue));
    }

    public void setMetadataValues(String contentItemId, Map<String, Object> metadataValues) {
        this.commandExecutor.execute(new SetMetaDataValuesCmd(contentItemId, metadataValues));
    }

    public void removeMetadataValue(String contentItemId, String metadataName) {
        this.commandExecutor.execute(new RemoveMetadataValueCmd(contentItemId, metadataName));
    }

    public void removeMetadataValues(String contentItemId, Collection<String> metadataNames) {
        this.commandExecutor.execute(new RemoveMetadataValuesCmd(contentItemId, metadataNames));
    }
}
