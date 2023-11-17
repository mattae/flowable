package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.api.RenditionItem;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.content.api.ContentObject;
import org.flowable.content.api.ContentStorage;

import java.io.InputStream;
import java.io.Serializable;

public class GetRenditionItemStreamCmd implements Command<InputStream>, Serializable {
    private static final long serialVersionUID = 1L;

    protected String renditionItemId;

    public GetRenditionItemStreamCmd(String renditionItemId) {
        this.renditionItemId = renditionItemId;
    }

    public InputStream execute(CommandContext commandContext) {
        if (this.renditionItemId == null)
            throw new FlowableIllegalArgumentException("renditionItemId is null");
        RenditionItem renditionItem = CommandContextUtil.getRenditionItemEntityManager().findById(this.renditionItemId);
        if (renditionItem == null)
            throw new FlowableObjectNotFoundException("content item could not be found with id " + this.renditionItemId);
        ContentStorage contentStorage = CommandContextUtil.getContentEngineConfiguration().getContentStorage();
        ContentObject contentObject = contentStorage.getContentObject(renditionItem.getContentStoreId());
        return contentObject.getContent();
    }
}
