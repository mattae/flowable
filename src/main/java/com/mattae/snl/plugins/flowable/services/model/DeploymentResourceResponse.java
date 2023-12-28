package com.mattae.snl.plugins.flowable.services.model;

public class DeploymentResourceResponse {
    private String id;

    private String url;

    private String contentUrl;

    private String mediaType;

    private String type;

    public DeploymentResourceResponse(String resourceId, String url, String contentUrl, String mediaType, String type) {
        setId(resourceId);
        setUrl(url);
        setContentUrl(contentUrl);
        setMediaType(mediaType);
        this.type = type;
        if (type == null)
            this.type = "resource";
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentUrl() {
        return this.contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getMediaType() {
        return this.mediaType;
    }

    public void setMediaType(String mimeType) {
        this.mediaType = mimeType;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
