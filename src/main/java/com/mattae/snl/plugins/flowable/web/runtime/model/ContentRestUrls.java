package com.mattae.snl.plugins.flowable.web.runtime.model;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

public final class ContentRestUrls {
    public static final String SEGMENT_REPOSITORY_RESOURCES = "document-repository";

    public static final String SEGMENT_CONTENT_SERVICE_RESOURCES = "content-service";

    public static final String SEGMENT_DEPLOYMENT_RESOURCE = "deployments";

    public static final String SEGMENT_DOCUMENT_DEFINITION_RESOURCE = "document-definitions";

    public static final String SEGMENT_DEPLOYMENT_ARTIFACT_RESOURCE = "resources";

    public static final String SEGMENT_DEPLOYMENT_ARTIFACT_RESOURCE_CONTENT = "resourcedata";

    public static final String SEGMENT_CONTENT_ITEMS_RESOURCE = "content-items";

    public static final String SEGMENT_VERSION_PARENT_ITEMS_RESOURCE = "version-parent-items";

    public static final String SEGMENT_QUERY_RESOURCE = "query";

    public static final String SEGMENT_CONTENT_ITEM_DATA = "data";

    public static final String SEGMENT_CONTENT_ITEM_METADATA = "metadata";

    public static final String SEGMENT_RENDITION_SERVICE_RESOURCES = "rendition-service";

    public static final String SEGMENT_RENDITION_ITEMS_RESOURCE = "rendition-items";

    public static final String SEGMENT_RENDITION_ITEM_DATA = "data";

    public static final String SEGMENT_RENDITION = "rendition";

    public static final String[] URL_DEPLOYMENT_COLLECTION = new String[]{"document-repository", "deployments"};

    public static final String[] URL_DEPLOYMENT = new String[]{"document-repository", "deployments", "{0}"};

    public static final String[] URL_DEPLOYMENT_RESOURCES = new String[]{"document-repository", "deployments", "{0}", "resources"};

    public static final String[] URL_DEPLOYMENT_RESOURCE = new String[]{"document-repository", "deployments", "{0}", "resources", "{1}"};

    public static final String[] URL_DEPLOYMENT_RESOURCE_CONTENT = new String[]{"document-repository", "deployments", "{0}", "resourcedata", "{1}"};

    public static final String[] URL_DOCUMENT_DEFINITION_COLLECTION = new String[]{"document-repository", "document-definitions"};

    public static final String[] URL_DOCUMENT_DEFINITION = new String[]{"document-repository", "document-definitions", "{0}"};

    public static final String[] URL_DOCUMENT_DEFINITION_RESOURCE_CONTENT = new String[]{"document-repository", "document-definitions", "{0}", "resourcedata"};

    public static final String[] URL_CONTENT_ITEM_COLLECTION = new String[]{"content-service", "content-items"};

    public static final String[] URL_CONTENT_ITEM = new String[]{"content-service", "content-items", "{0}"};

    public static final String[] URL_CONTENT_ITEM_DATA = new String[]{"content-service", "content-items", "{0}", "data"};

    public static final String[] URL_CONTENT_ITEM_METADATA = new String[]{"content-service", "content-items", "{0}", "metadata"};

    public static final String[] URL_CONTENT_ITEM_METADATA_VALUE = new String[]{"content-service", "content-items", "{0}", "metadata", "{1}"};

    public static final String[] URL_VERSION_PARENT_ITEM = new String[]{"content-service", "version-parent-items", "{0}"};

    public static final String[] URL_VERSION_PARENT_ITEM_DATA = new String[]{"content-service", "version-parent-items", "{0}", "data"};

    public static final String[] URL_QUERY_CONTENT_ITEM = new String[]{"query", "content-items"};

    public static final String[] URL_RENDITION_ITEM_COLLECTION = new String[]{"rendition-service", "rendition-items"};

    public static final String[] URL_RENDITION_ITEM = new String[]{"rendition-service", "rendition-items", "{0}"};

    public static final String[] URL_RENDITION_ITEM_DATA = new String[]{"rendition-service", "rendition-items", "{0}", "data"};

    public static final String[] URL_QUERY_RENDITION_ITEM = new String[]{"query", "rendition-items"};

    public static final String[] URL_CONTENT_ITEM_RENDITION_DATA = new String[]{"content-service", "content-items", "{0}", "rendition", "{1}"};

    public static final String[] URL_VERSION_PARENT_ITEM_RENDITION_DATA = new String[]{"content-service", "version-parent-items", "{0}", "rendition", "{1}"};

    public static String createRelativeResourceUrl(String[] segments, Object... arguments) {
        return MessageFormat.format(StringUtils.join(segments, '/'), arguments);
    }
}
