package com.mattae.snl.plugins.flowable.content.deployer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattae.snl.plugins.flowable.content.api.BaseDocumentDefinitionModel;
import com.mattae.snl.plugins.flowable.content.api.DocumentDefinitionModel;
import com.mattae.snl.plugins.flowable.content.api.DocumentResourceConverter;
import org.flowable.common.engine.api.FlowableException;

import java.io.InputStream;

public class DocumentResourceConverterImpl implements DocumentResourceConverter {
    protected ObjectMapper objectMapper;

    public DocumentResourceConverterImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public DocumentDefinitionModel convertDocumentResourceToModel(byte[] documentResourceBytes) {
        try {
            return (DocumentDefinitionModel) this.objectMapper.readValue(documentResourceBytes, BaseDocumentDefinitionModel.class);
        } catch (Exception e) {
            throw new FlowableException("Error reading document definition resource", e);
        }
    }

    public DocumentDefinitionModel convertDocumentResourceToModel(InputStream documentResourceStream) {
        try {
            return (DocumentDefinitionModel) this.objectMapper.readValue(documentResourceStream, BaseDocumentDefinitionModel.class);
        } catch (Exception e) {
            throw new FlowableException("Error reading document definition resource", e);
        }
    }

    public String convertDocumentDefinitionModelToJson(DocumentDefinitionModel documentDefinitionModel) {
        try {
            return this.objectMapper.writeValueAsString(documentDefinitionModel);
        } catch (Exception e) {
            throw new FlowableException("Error writing document definition model to json", e);
        }
    }
}
