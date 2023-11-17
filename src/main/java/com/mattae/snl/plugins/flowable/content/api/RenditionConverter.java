package com.mattae.snl.plugins.flowable.content.api;

import java.io.InputStream;
import java.util.List;

public interface RenditionConverter {
    ContentRendition generatePDF(InputStream paramInputStream) throws Exception;

    ContentRendition generateThumbnail(InputStream paramInputStream) throws Exception;

    String getPdfRenditionMimeType();

    String getThumbnailRenditionMimeType();

    boolean handlesContentType(String paramString);

    void addSupportedContentType(String paramString);

    void setSupportedContentTypes(List<String> paramList);

    boolean isPDFRenditionSupported();

    boolean isThumbnailRenditionSupported();
}
