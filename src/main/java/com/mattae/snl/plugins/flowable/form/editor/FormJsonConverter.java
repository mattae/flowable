package com.mattae.snl.plugins.flowable.form.editor;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mattae.snl.plugins.flowable.form.model.ComponentDeserializer;
import com.mattae.snl.plugins.flowable.form.model.FormIOFormModel;
import org.flowable.editor.form.converter.FlowableFormJsonException;
import org.flowable.form.model.FormField;

public class FormJsonConverter extends org.flowable.editor.form.converter.FormJsonConverter {

    protected ObjectMapper objectMapper = new ObjectMapper();

    public FormJsonConverter() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(FormIOFormModel.Component.class, new ComponentDeserializer());
        objectMapper.registerModule(module)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)
            .addMixIn(FormField.class, NoTypes.class);
    }

    public FormIOFormModel convertToFormModel(String modelJson) {
        try {
            return objectMapper.readValue(modelJson, FormIOFormModel.class);
        } catch (Exception e) {
            throw new FlowableFormJsonException("Error reading form json", e);
        }
    }

    public String convertToJson(FormIOFormModel definition) {
        try {
            return objectMapper.writeValueAsString(definition);
        } catch (Exception e) {
            throw new FlowableFormJsonException("Error writing form json", e);
        }
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    static
    class NoTypes {
    }
}
