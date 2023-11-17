package com.mattae.snl.plugins.flowable.content.services;

import com.mattae.snl.plugins.flowable.content.engine.impl.ContentItemQueryImpl;
import com.mattae.snl.plugins.flowable.content.engine.impl.cmd.DeleteContentItemCmd;
import com.mattae.snl.plugins.flowable.content.engine.impl.cmd.DeleteContentItemsCmd;
import com.mattae.snl.plugins.flowable.content.engine.impl.cmd.SaveContentItemCmd;
import org.flowable.content.api.ContentItem;
import org.flowable.content.api.ContentItemQuery;

import java.io.InputStream;

public class ContentServiceImpl extends org.flowable.content.engine.impl.ContentServiceImpl {
    @Override
    public void saveContentItem(ContentItem contentItem) {
        commandExecutor.execute(new SaveContentItemCmd(contentItem));
    }

    @Override
    public void saveContentItem(ContentItem contentItem, InputStream inputStream) {
        commandExecutor.execute(new SaveContentItemCmd(contentItem, inputStream));
    }

    public void deleteContentItem(String contentItemId) {
        this.commandExecutor.execute(new DeleteContentItemCmd(contentItemId));
    }

    public void deleteContentItemsByProcessInstanceId(String processInstanceId) {
        this.commandExecutor.execute(new DeleteContentItemsCmd(processInstanceId, null, null, null));
    }

    public void deleteContentItemsByTaskId(String taskId) {
        this.commandExecutor.execute(new DeleteContentItemsCmd(null, taskId, null, null));
    }

    public void deleteContentItemsByScopeIdAndScopeType(String scopeId, String scopeType) {
        this.commandExecutor.execute(new DeleteContentItemsCmd(null, null, scopeId, scopeType));
    }

    public ContentItemQuery createContentItemQuery() {
        return new ContentItemQueryImpl(this.commandExecutor);
    }
}
