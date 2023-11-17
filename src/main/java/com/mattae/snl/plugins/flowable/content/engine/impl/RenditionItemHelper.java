package com.mattae.snl.plugins.flowable.content.engine.impl;

import com.mattae.snl.plugins.flowable.content.api.ContentRendition;
import com.mattae.snl.plugins.flowable.content.api.ContentRenditionManager;
import com.mattae.snl.plugins.flowable.content.config.ContentEngineConfiguration;
import com.mattae.snl.plugins.flowable.content.engine.impl.persistence.entity.RenditionItemEntity;
import com.mattae.snl.plugins.flowable.content.engine.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.content.api.ContentItem;
import org.flowable.content.api.ContentObject;
import org.flowable.content.api.ContentService;
import org.flowable.content.api.ContentStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

public class RenditionItemHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RenditionItemHelper.class);

    public static void createContentRenditions(RenditionItemEntity renditionItem) {
        ContentEngineConfiguration contentEngineConfiguration = CommandContextUtil.getContentEngineConfiguration();
        ContentService contentService = contentEngineConfiguration.getContentService();
        ContentItem contentItem = contentService.createContentItemQuery().id(renditionItem.getContentItemId()).singleResult();
        InputStream contentObjectInputStream = getContentObjectInputStream(contentItem.getContentStoreId());
        try {
            Optional<ContentRendition> contentRendition = createRendition(renditionItem, contentItem.getName(), contentObjectInputStream);
            contentRendition.ifPresent(rendition -> {
                renditionItem.setName(rendition.getContentRendition().getName());
                renditionItem.setMimeType(rendition.getContentRenditionMimeType());
                renditionItem.setProvisional(false);
                renditionItem.setContentAvailable(true);
                try {
                    InputStream thumbnailFis = Files.newInputStream(rendition.getContentRendition().toPath());
                    try {
                        contentEngineConfiguration.getRenditionService().saveRenditionItem(renditionItem, thumbnailFis);
                        thumbnailFis.close();
                    } catch (Throwable throwable) {
                        try {
                            thumbnailFis.close();
                        } catch (Throwable throwable1) {
                            throwable.addSuppressed(throwable1);
                        }
                        throw throwable;
                    }
                } catch (Exception e) {
                    throw new FlowableException("Error while storing thumbnail rendition", e);
                }
            });
        } catch (FlowableException fe) {
            LOGGER.error("Error while creating thumbnail rendition", fe);
        }
    }

    protected static Optional<ContentRendition> createRendition(RenditionItemEntity renditionItem, String name, InputStream contentObjectInputStream) {
        ContentEngineConfiguration contentEngineConfiguration = CommandContextUtil.getContentEngineConfiguration();
        ContentRenditionManager contentRenditionManager = contentEngineConfiguration.getContentRenditionManager();
        if (contentRenditionManager.getPdfRenditionDefaultMimeType().equals(renditionItem.getMimeType()))
            return Optional.ofNullable(contentRenditionManager.createPdfRendition(name, contentObjectInputStream));
        if (contentRenditionManager.getThumbnailRenditionDefaultMimeType().equals(renditionItem.getMimeType()))
            return Optional.ofNullable(contentRenditionManager.createThumbnailRendition(name, contentObjectInputStream));
        throw new FlowableException("unknown rendition mime type: " + renditionItem.getMimeType());
    }

    protected static InputStream getContentObjectInputStream(String contentStoreId) {
        ContentEngineConfiguration contentEngineConfiguration = CommandContextUtil.getContentEngineConfiguration();
        ContentStorage contentStorage = contentEngineConfiguration.getContentStorage();
        ContentObject contentObject = contentStorage.getContentObject(contentStoreId);

        return contentObject.getContent();
    }
}
