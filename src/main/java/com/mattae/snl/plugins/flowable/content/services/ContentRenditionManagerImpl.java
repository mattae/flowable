package com.mattae.snl.plugins.flowable.content.services;

import com.mattae.snl.plugins.flowable.content.api.ContentRendition;
import com.mattae.snl.plugins.flowable.content.api.ContentRenditionManager;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class ContentRenditionManagerImpl implements ContentRenditionManager {
    @Override
    public ContentRendition createPdfRendition(String paramString, InputStream paramInputStream) {
        return null;
    }

    @Override
    public ContentRendition createThumbnailRendition(String paramString, InputStream paramInputStream) {
        return null;
    }

    @Override
    public boolean isPdfRenditionSupported(String paramString) {
        return false;
    }

    @Override
    public boolean isThumbnailRenditionSupported(String paramString) {
        return false;
    }

    @Override
    public String getPdfRenditionDefaultMimeType() {
        return null;
    }

    @Override
    public String getThumbnailRenditionDefaultMimeType() {
        return null;
    }

    @Override
    public String getMediaType(String paramString, InputStream paramInputStream) {
        return null;
    }
}
