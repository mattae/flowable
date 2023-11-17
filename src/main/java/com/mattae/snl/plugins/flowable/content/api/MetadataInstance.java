package com.mattae.snl.plugins.flowable.content.api;

public interface MetadataInstance {
    String getId();

    String getName();

    String getContentItemId();

    String getTextValue();

    String getTextValue2();

    Long getLongValue();

    Double getDoubleValue();

    byte[] getBytes();

    Object getValue();

    String getTypeName();
}
