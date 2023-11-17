package com.mattae.snl.plugins.flowable.content.engine.impl;

import com.mattae.snl.plugins.flowable.content.config.ContentEngineConfiguration;
import com.mattae.snl.plugins.flowable.content.engine.impl.cmd.GetTableNameCmd;

public class ContentManagementServiceImpl extends org.flowable.content.engine.impl.ContentManagementServiceImpl {
    public ContentManagementServiceImpl(ContentEngineConfiguration engineConfiguration) {
        super(engineConfiguration);
    }

    public String getTableName(Class<?> entityClass) {
        return this.commandExecutor.execute(new GetTableNameCmd(entityClass));
    }
}
