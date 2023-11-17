package com.mattae.snl.plugins.flowable.content.engine.impl.cmd;

import com.mattae.snl.plugins.flowable.content.api.RenditionItem;
import com.mattae.snl.plugins.flowable.content.config.ContentEngineConfiguration;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.RenditionItemEntity;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.content.api.ContentObject;
import org.flowable.content.api.ContentObjectStorageMetadata;
import org.flowable.content.api.ContentStorage;

import java.io.InputStream;
import java.io.Serializable;

public class SaveRenditionItemCmd implements Command<Void>, Serializable {
    private static final long serialVersionUID = 1L;

    protected RenditionItem renditionItem;

    protected InputStream inputStream;

    public SaveRenditionItemCmd(RenditionItem renditionItem) {
        this.renditionItem = renditionItem;
    }

    public SaveRenditionItemCmd(RenditionItem renditionItem, InputStream inputStream) {
        this.renditionItem = renditionItem;
        this.inputStream = inputStream;
    }

    public Void execute(CommandContext commandContext) {
        if (this.renditionItem == null)
            throw new FlowableIllegalArgumentException("renditionItem is null");
        if (!(this.renditionItem instanceof RenditionItemEntity renditionItemEntity))
            throw new FlowableIllegalArgumentException("renditionItem is not of type RenditionItemEntity");
        ContentEngineConfiguration contentEngineConfiguration = CommandContextUtil.getContentEngineConfiguration();
        if (this.inputStream != null) {
            ContentStorage contentStorage = contentEngineConfiguration.getContentStorage();
            ContentObjectStorageMetadata coreContentObjectStorageMetadata = contentEngineConfiguration
                .getRenditionItemContentObjectStorageMetadataProvider().createStorageMetadata(renditionItemEntity);
            ContentObject createContentObject = contentStorage.createContentObject(this.inputStream, coreContentObjectStorageMetadata);
            renditionItemEntity.setContentStoreId(createContentObject.getId());
            renditionItemEntity.setContentStoreName(contentStorage.getContentStoreName());
            renditionItemEntity.setContentAvailable(true);
            renditionItemEntity.setContentSize(createContentObject.getContentLength());
        }
        if (renditionItemEntity.getLastModified() == null)
            renditionItemEntity.setLastModified(contentEngineConfiguration.getClock().getCurrentTime());
        if (this.renditionItem.getId() == null) {
            if (renditionItemEntity.getCreated() == null)
                renditionItemEntity.setCreated(contentEngineConfiguration.getClock().getCurrentTime());
            CommandContextUtil.getRenditionItemEntityManager().insert(renditionItemEntity);
        } else {
            CommandContextUtil.getRenditionItemEntityManager().update(renditionItemEntity);
        }
        return null;
    }
}
