package com.mattae.snl.plugins.flowable.content.rendition;

import com.mattae.snl.plugins.flowable.content.api.ContentRendition;
import com.mattae.snl.plugins.flowable.content.api.ContentRenditionManager;
import com.mattae.snl.plugins.flowable.content.api.RenditionConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.mime.MediaType;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.job.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;

public class DefaultContentRenditionManager extends ServiceImpl implements ContentRenditionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultContentRenditionManager.class);

    protected List<RenditionConverter> contentConverters;

    public DefaultContentRenditionManager(List<RenditionConverter> contentConverters) {
        this.contentConverters = contentConverters;
    }

    public ContentRendition createPdfRendition(String name, InputStream inputStream) {
        if (inputStream == null)
            throw new IllegalArgumentException("input stream is required");
        LOGGER.debug("Start generating rendition");
        ContentRendition rendition = null;
        try {
            TikaInputStream stream = TikaInputStream.get(new BufferedInputStream(inputStream));
            String mediaSubType = getMediaSubType(name, stream);
            RenditionConverter contentConverter = handleTypeConversions(mediaSubType);
            if (contentConverter != null) {
                rendition = contentConverter.generatePDF(stream);
            } else {
                LOGGER.info(String.format("Not generating PDF rendition; no converter found for mime type %s", mediaSubType));
            }
        } catch (Exception ex) {
            LOGGER.error("Error while creating rendition: ", ex);
        }
        return rendition;
    }

    public ContentRendition createThumbnailRendition(String name, InputStream inputStream) {
        if (inputStream == null)
            throw new IllegalArgumentException("input stream is required");
        LOGGER.debug("Start generating thumbnail");
        ContentRendition thumbnail = null;
        try {
            TikaInputStream stream = TikaInputStream.get(new BufferedInputStream(inputStream));
            String mediaSubType = getMediaSubType(name, stream);
            RenditionConverter contentConverter = handleTypeConversions(mediaSubType);
            if (contentConverter != null) {
                thumbnail = contentConverter.generateThumbnail(stream);
            } else {
                LOGGER.info(String.format("Not generating thumbnail rendition; no converter found for mime type %s", mediaSubType));
            }
        } catch (Exception ex) {
            LOGGER.error("Error while creating thumbnail rendition: ", ex);
        }
        return thumbnail;
    }

    public boolean isPdfRenditionSupported(String mimeType) {
        if (StringUtils.isEmpty(mimeType))
            throw new IllegalArgumentException("mime type is required");
        MediaType mediaType = MediaType.parse(mimeType);
        if (mediaType == null)
            return false;
        RenditionConverter contentConverter = handleTypeConversions(mediaType.getSubtype());
        if (contentConverter != null)
            return contentConverter.isPDFRenditionSupported();
        return false;
    }

    public boolean isThumbnailRenditionSupported(String mimeType) {
        if (StringUtils.isEmpty(mimeType))
            throw new IllegalArgumentException("mime type is required");
        MediaType mediaType = MediaType.parse(mimeType);
        if (mediaType == null)
            return false;
        RenditionConverter contentConverter = handleTypeConversions(mediaType.getSubtype());
        if (contentConverter != null)
            return contentConverter.isThumbnailRenditionSupported();
        return false;
    }

    public String getPdfRenditionDefaultMimeType() {
        return "application/pdf";
    }

    public String getThumbnailRenditionDefaultMimeType() {
        return "image/jpeg";
    }

    protected RenditionConverter handleTypeConversions(String mimeType) {
        RenditionConverter matchedContentConverter = null;
        for (RenditionConverter contentConverter : this.contentConverters) {
            if (contentConverter.handlesContentType(mimeType)) {
                LOGGER.debug(String.format("mime type %s will be handled by converter %s", mimeType, contentConverter.getClass()));
                matchedContentConverter = contentConverter;
                break;
            }
        }
        return matchedContentConverter;
    }

    public String getMediaType(String name, InputStream inputStream) {
        String mediaTypeStr;
        try {
            MediaType mediaType = MetaDataUtil.determineMetaDataType(name, inputStream);
            if (mediaType == null || StringUtils.isEmpty(mediaType.getSubtype()))
                throw new FlowableException("could not detect media type for " + name);
            mediaTypeStr = mediaType.toString();
        } catch (FlowableException e) {
            throw e;
        } catch (Exception ex) {
            throw new FlowableException("Error while determining media type", ex);
        }
        return mediaTypeStr;
    }

    protected String getMediaSubType(String name, InputStream inputStream) {
        String mediaSubType;
        try {
            MediaType mediaType = MetaDataUtil.determineMetaDataType(name, inputStream);
            if (mediaType == null || StringUtils.isEmpty(mediaType.getSubtype()))
                throw new FlowableException("could not detect media type for " + name);
            mediaSubType = mediaType.getSubtype();
        } catch (FlowableException e) {
            throw e;
        } catch (Exception ex) {
            throw new FlowableException("Error while determining media sub type", ex);
        }
        return mediaSubType;
    }
}
