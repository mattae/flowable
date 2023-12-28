package com.mattae.snl.plugins.flowable.content.engine.impl;

import com.mattae.snl.plugins.flowable.content.api.RenditionItem;
import com.mattae.snl.plugins.flowable.content.api.RenditionItemQuery;
import com.mattae.snl.plugins.flowable.content.api.RenditionService;
import com.mattae.snl.plugins.flowable.content.config.ContentEngineConfiguration;
import com.mattae.snl.plugins.flowable.content.engine.impl.cmd.*;
import org.flowable.common.engine.impl.service.CommonEngineServiceImpl;

import java.io.InputStream;

public class RenditionServiceImpl extends CommonEngineServiceImpl<ContentEngineConfiguration> implements RenditionService {
    public RenditionItem newRenditionItem() {
        return this.commandExecutor.execute(new CreateRenditionItemCmd());
    }

    public void saveRenditionItem(RenditionItem renditionItem) {
        this.commandExecutor.execute(new SaveRenditionItemCmd(renditionItem));
    }

    public void saveRenditionItem(RenditionItem renditionItem, InputStream inputStream) {
        this.commandExecutor.execute(new SaveRenditionItemCmd(renditionItem, inputStream));
    }

    public InputStream getRenditionItemData(String renditionItemId) {
        return this.commandExecutor.execute(new GetRenditionItemStreamCmd(renditionItemId));
    }

    public void deleteRenditionItem(String renditionItemId) {
        this.commandExecutor.execute(new DeleteRenditionItemCmd(renditionItemId));
    }

    public void deleteRenditionItemsByProcessInstanceId(String processInstanceId) {
        this.commandExecutor.execute(new DeleteRenditionItemsByProcessInstanceIdCmd(processInstanceId));
    }

    public void deleteRenditionItemsByTaskId(String taskId) {
        this.commandExecutor.execute(new DeleteRenditionItemsByTaskIdCmd(taskId));
    }

    public void deleteRenditionItemsByScopeIdAndScopeType(String scopeId, String scopeType) {
        this.commandExecutor.execute(new DeleteRenditionItemsByScopeCmd(scopeId, scopeType));
    }

    public RenditionItemQuery createRenditionItemQuery() {
        return new RenditionItemQueryImpl(this.commandExecutor);
    }
}
