package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.content.api.ContentItem;
import org.flowable.content.engine.impl.cmd.AbstractDeleteContentItemCmd;
import org.flowable.content.engine.impl.persistence.entity.ContentItemEntityManager;

public class DeleteContentItemsCmd extends AbstractDeleteContentItemCmd implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String processInstanceId;
    protected String taskId;
    protected String scopeId;
    protected String scopeType;

    public DeleteContentItemsCmd(String processInstanceId, String taskId, String scopeId, String scopeType) {
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.scopeId = scopeId;
        this.scopeType = scopeType;
    }

    public Void execute(CommandContext commandContext) {
        if (this.processInstanceId == null && this.taskId == null && this.scopeId == null) {
            throw new FlowableIllegalArgumentException("taskId, processInstanceId and scopeId are null");
        } else {
            ContentItemEntityManager contentItemEntityManager = CommandContextUtil.getContentItemEntityManager();
            List contentItems;
            Iterator var4;
            ContentItem contentItem;
            if (this.processInstanceId != null) {
                contentItems = contentItemEntityManager.findContentItemsByProcessInstanceId(this.processInstanceId);
                if (contentItems != null) {
                    var4 = contentItems.iterator();

                    while(var4.hasNext()) {
                        contentItem = (ContentItem)var4.next();
                        this.deleteContentItemInContentStorage(contentItem);
                    }
                }

                contentItemEntityManager.deleteContentItemsByProcessInstanceId(this.processInstanceId);
            } else if (StringUtils.isNotEmpty(this.scopeId)) {
                contentItems = contentItemEntityManager.findContentItemsByScopeIdAndScopeType(this.scopeId, this.scopeType);
                if (contentItems != null) {
                    var4 = contentItems.iterator();

                    while(var4.hasNext()) {
                        contentItem = (ContentItem)var4.next();
                        this.deleteContentItemInContentStorage(contentItem);
                    }
                }

                contentItemEntityManager.deleteContentItemsByScopeIdAndScopeType(this.scopeId, this.scopeType);
            } else {
                contentItems = contentItemEntityManager.findContentItemsByTaskId(this.taskId);
                if (contentItems != null) {
                    var4 = contentItems.iterator();

                    while(var4.hasNext()) {
                        contentItem = (ContentItem)var4.next();
                        this.deleteContentItemInContentStorage(contentItem);
                    }
                }

                contentItemEntityManager.deleteContentItemsByTaskId(this.taskId);
            }

            return null;
        }
    }
}
