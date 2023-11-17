package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.RenditionItemEntity;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.content.api.ContentStorage;

import java.io.Serializable;

public class DeleteRenditionItemCmd implements Command<Void>, Serializable {
    private static final long serialVersionUID = 1L;

    protected String renditionItemId;

    public DeleteRenditionItemCmd(String renditionItemId) {
        this.renditionItemId = renditionItemId;
    }

    public Void execute(CommandContext commandContext) {
        if (this.renditionItemId == null)
            throw new FlowableIllegalArgumentException("renditionItemId is null");
        RenditionItemEntity renditionItem = CommandContextUtil.getRenditionItemEntityManager().findById(this.renditionItemId);
        if (renditionItem == null)
            throw new FlowableObjectNotFoundException("rendition item could not be found with id " + this.renditionItemId);
        if (renditionItem.getContentStoreId() != null) {
            ContentStorage contentStorage = CommandContextUtil.getContentEngineConfiguration().getContentStorage();
            if (renditionItem.isContentAvailable())
                contentStorage.deleteContentObject(renditionItem.getContentStoreId());
        }
        CommandContextUtil.getRenditionItemEntityManager().delete(renditionItem);
        return null;
    }
}
