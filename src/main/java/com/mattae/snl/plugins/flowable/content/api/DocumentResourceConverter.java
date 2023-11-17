package com.mattae.snl.plugins.flowable.content.api;

import java.io.InputStream;

public interface DocumentResourceConverter {
    DocumentDefinitionModel convertDocumentResourceToModel(byte[] paramArrayOfbyte);

    DocumentDefinitionModel convertDocumentResourceToModel(InputStream paramInputStream);

    String convertDocumentDefinitionModelToJson(DocumentDefinitionModel paramDocumentDefinitionModel);
}
