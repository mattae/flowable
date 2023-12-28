package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.content.engine.impl.cmd.AbstractDeleteContentItemCmd;
import org.flowable.content.engine.impl.persistence.entity.ContentItemEntity;

import java.io.Serializable;

public class DeleteContentItemCmd extends AbstractDeleteContentItemCmd implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String contentItemId;

    public DeleteContentItemCmd(String contentItemId) {
        this.contentItemId = contentItemId;
    }

    public Void execute(CommandContext commandContext) {
        if (this.contentItemId == null) {
            throw new FlowableIllegalArgumentException("contentItemId is null");
        } else {
            ContentItemEntity contentItem = CommandContextUtil.getContentItemEntityManager().findById(this.contentItemId);
            if (contentItem == null) {
                throw new FlowableObjectNotFoundException("content item could not be found with id " + this.contentItemId);
            } else {
                this.deleteContentItemInContentStorage(contentItem);
                CommandContextUtil.getContentItemEntityManager().delete(contentItem);
                return null;
            }
        }
    }
}
