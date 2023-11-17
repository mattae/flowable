package com.mattae.snl.plugins.flowable.web.runtime;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class ResponseEntityHelper {
    protected Collection<MediaType> autoInlineMediaTypes;

    protected Charset charset = StandardCharsets.UTF_8;

    public ResponseEntityHelper(Collection<MediaType> autoInlineMediaTypes) {
        this.autoInlineMediaTypes = autoInlineMediaTypes;
    }

    public ResponseEntity<Resource> asByteArrayResponseEntity(InputStream inputStream, String mimeType, String name, Boolean download) {
        HttpHeaders responseHeaders = new HttpHeaders();
        MediaType mediaType = null;
        if (mimeType != null)
            try {
                mediaType = MediaType.valueOf(mimeType);
                responseHeaders.setContentType(mediaType);
            } catch (Exception ignored) {
            }
        if (mediaType == null)
            responseHeaders.set("Content-Type", "application/octet-stream");
        if (name != null) {
            String dispositionType = dispositionType(download, mediaType);
            responseHeaders.setContentDisposition(ContentDisposition.builder(dispositionType).filename(name, this.charset).build());
        }
        return new ResponseEntity<>(new InputStreamResource(inputStream), responseHeaders, HttpStatus.OK);
    }

    protected String dispositionType(Boolean download, MediaType mediaType) {
        if (download == null) {
            for (MediaType autoInlineMediaType : this.autoInlineMediaTypes) {
                if (autoInlineMediaType.includes(mediaType))
                    return "inline";
            }
            return "attachment";
        }
        return download.booleanValue() ? "attachment" : "inline";
    }

    public void setCharset(Charset charset) {
        Assert.notNull(charset, "charset must not be null");
        this.charset = charset;
    }
}
