package com.mattae.snl.plugins.flowable.content.api;

@FunctionalInterface
public interface RenditionItemContentObjectStorageMetadataProvider {
    ContentObjectStorageMetadata createStorageMetadata(RenditionItem paramRenditionItem);
}
