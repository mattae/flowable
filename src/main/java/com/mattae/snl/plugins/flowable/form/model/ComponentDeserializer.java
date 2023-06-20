package com.mattae.snl.plugins.flowable.form.model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class ComponentDeserializer extends StdDeserializer<FormIOFormModel.Component> {

    public ComponentDeserializer() {
        this(null);
    }

    protected ComponentDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public FormIOFormModel.Component deserialize(JsonParser p, DeserializationContext context) throws IOException, JacksonException {

        final ObjectMapper mapper = (ObjectMapper) p.getCodec();
        final JsonNode component = mapper.readTree(p);
        FieldTypes type = Arrays.stream(FieldTypes.values())
            .filter(f -> f.name().equals(component.at("/type").asText()))
            .findFirst().orElse(null);
        if (type != null) {
            switch (type) {
                case panel -> {
                    return mapper.treeToValue(component, FormIOFormModel.Panel.class);
                }
                case textfield -> {
                    return mapper.treeToValue(component, FormIOFormModel.TextField.class);
                }
                case columns -> {
                    return mapper.treeToValue(component, FormIOFormModel.Column.class);
                }
                case content -> {
                    return mapper.treeToValue(component, FormIOFormModel.Content.class);
                }
                case signature -> {
                    return mapper.treeToValue(component, FormIOFormModel.Signature.class);
                }
                case survey -> {
                    return mapper.treeToValue(component, FormIOFormModel.Survey.class);
                }
                case select -> {
                    return mapper.treeToValue(component, FormIOFormModel.Select.class);
                }
                case textarea -> {
                    return mapper.treeToValue(component, FormIOFormModel.Textarea.class);
                }
                case number -> {
                    return mapper.treeToValue(component, FormIOFormModel.Number.class);
                }
                case fieldset -> {
                    return mapper.treeToValue(component, FormIOFormModel.Fieldset.class);
                }
                case datagrid -> {
                    return mapper.treeToValue(component, FormIOFormModel.Datagrid.class);
                }
                case email -> {
                    return mapper.treeToValue(component, FormIOFormModel.Email.class);
                }
                case phoneNumber -> {
                    return mapper.treeToValue(component, FormIOFormModel.PhoneNumber.class);
                }
                case button -> {
                    return mapper.treeToValue(component, FormIOFormModel.Button.class);
                }
                case checkbox -> {
                    return mapper.treeToValue(component, FormIOFormModel.Checkbox.class);
                }
                case currency -> {
                    return mapper.treeToValue(component, FormIOFormModel.Currency.class);
                }
                case datetime -> {
                    return mapper.treeToValue(component, FormIOFormModel.Datetime.class);
                }
                case editgrid -> {
                    return mapper.treeToValue(component, FormIOFormModel.EditGrid.class);
                }
                case htmlelement -> {
                    return mapper.treeToValue(component, FormIOFormModel.HtmlElement.class);
                }
                case file -> {
                    return mapper.treeToValue(component, FormIOFormModel.File.class);
                }
                case upload -> {
                    return mapper.treeToValue(component, FormIOFormModel.Upload.class);
                }
                case password -> {
                    return mapper.treeToValue(component, FormIOFormModel.Password.class);
                }
                case radio -> {
                    return mapper.treeToValue(component, FormIOFormModel.Radio.class);
                }
                case selectBoxes -> {
                    return mapper.treeToValue(component, FormIOFormModel.SelectBoxes.class);
                }
                case tabs -> {
                    return mapper.treeToValue(component, FormIOFormModel.Tabs.class);
                }
                case tags -> {
                    return mapper.treeToValue(component, FormIOFormModel.Tags.class);
                }
                case time -> {
                    return mapper.treeToValue(component, FormIOFormModel.Time.class);
                }
                case url -> {
                    return mapper.treeToValue(component, FormIOFormModel.Url.class);
                }
                case well -> {
                    return mapper.treeToValue(component, FormIOFormModel.Well.class);
                }
                case table -> {
                    return mapper.treeToValue(component, FormIOFormModel.Table.class);
                }
                default -> throw new IllegalStateException("Unexpected Component type : " + component.at("/type"));
            }
        }
        return null;
    }
}
