package com.mattae.snl.plugins.flowable.content.api;

public interface DocumentDefinitionBuilder {
    String getName();

    DocumentDefinitionBuilder name(String paramString);

    String getDescription();

    DocumentDefinitionBuilder description(String paramString);

    DocumentDefinition create();
}
