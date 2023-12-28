package com.mattae.snl.plugins.flowable.content.api;

import java.io.InputStream;

public interface ContentRenditionManager {
    public enum RenditionType {
        PDF, THUMBNAIL
    }
    ContentRendition createPdfRendition(String paramString, InputStream paramInputStream);

    ContentRendition createThumbnailRendition(String paramString, InputStream paramInputStream);

    boolean isPdfRenditionSupported(String paramString);

    boolean isThumbnailRenditionSupported(String paramString);

    String getPdfRenditionDefaultMimeType();

    String getThumbnailRenditionDefaultMimeType();

    String getMediaType(String paramString, InputStream paramInputStream);
}
