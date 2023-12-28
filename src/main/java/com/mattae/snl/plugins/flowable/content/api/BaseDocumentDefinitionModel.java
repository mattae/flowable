package com.mattae.snl.plugins.flowable.content.api;

import lombok.Getter;

@Getter
public class BaseDocumentDefinitionModel implements DocumentDefinitionModel {
    protected String key;

    protected String name;

    protected String description;
    protected String versioning;

    protected String formKey;

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVersioning(String versioning) {
        this.versioning = versioning;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}
