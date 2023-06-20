/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mattae.snl.plugins.flowable.form.cmd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattae.snl.plugins.flowable.form.model.FieldTypes;
import com.mattae.snl.plugins.flowable.form.model.FormIOFormModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.common.engine.impl.el.VariableContainerWrapper;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.form.api.FormInstance;
import org.flowable.form.api.FormInstanceInfo;
import org.flowable.form.engine.FormEngineConfiguration;
import org.flowable.form.engine.impl.util.CommandContextUtil;
import org.flowable.form.model.*;
import org.joda.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Tijs Rademakers
 */
@Slf4j
public class AbstractGetFormInstanceModelCmd extends org.flowable.form.engine.impl.cmd.AbstractGetFormInstanceModelCmd {

    protected void fillFormFieldValues(FormInstance formInstance, FormInstanceInfo formInstanceModel, CommandContext commandContext) {

        FormEngineConfiguration formEngineConfiguration = CommandContextUtil.getFormEngineConfiguration();
        FormIOFormModel formModel = (FormIOFormModel) formInstanceModel.getFormModel();
        List<FormField> allFields = formModel.listAllFields();
        if (allFields != null) {

            Map<String, JsonNode> formInstanceFieldMap = new HashMap<>();
            if (formInstance != null) {
                fillFormInstanceValues(formInstanceModel, formInstance, formInstanceFieldMap, formEngineConfiguration.getObjectMapper());
                fillVariablesWithFormValues(formInstanceFieldMap, allFields);
            }

            for (FormField field : allFields) {
                if (field instanceof OptionFormField) {
                    OptionFormField optionFormField = (OptionFormField) field;
                    if (optionFormField.getOptionsExpression() != null) {
                        // Drop down options to be populated from an expression
                        Expression optionsExpression = formEngineConfiguration.getExpressionManager().createExpression(optionFormField.getOptionsExpression());
                        Object value = null;
                        try {
                            value = optionsExpression.getValue(new VariableContainerWrapper(variables));
                        } catch (Exception e) {
                            throw new FlowableException("Error getting value for optionsExpression: " + optionFormField.getOptionsExpression(), e);
                        }
                        if (value instanceof List) {
                            @SuppressWarnings("unchecked")
                            List<Option> options = (List<Option>) value;
                            optionFormField.setOptions(options);
                        } else if (value instanceof String) {
                            String json = (String) value;
                            try {
                                List<Option> options = formEngineConfiguration.getObjectMapper().readValue(json, new TypeReference<List<Option>>() {
                                });
                                optionFormField.setOptions(options);
                            } catch (Exception e) {
                                throw new FlowableException("Error parsing optionsExpression json value: " + json, e);
                            }
                        } else {
                            throw new FlowableException("Invalid type from evaluated expression for optionsExpression: " + optionFormField.getOptionsExpression() + ", resulting type:" + value.getClass().getName());
                        }
                    }
                    Object variableValue = variables.get(field.getId());
                    optionFormField.setValue(variableValue);

                } else if (FormFieldTypes.HYPERLINK.equals(field.getType())) {
                    Object variableValue = variables.get(field.getId());
                    // process expression if there is no value, otherwise keep it
                    if (variableValue != null) {
                        field.setValue(variableValue);
                    } else {
                        // No value set, process as expression
                        if (field.getParam("hyperlinkUrl") != null) {
                            String hyperlinkUrl = field.getParam("hyperlinkUrl").toString();
                            Expression formExpression = formEngineConfiguration.getExpressionManager().createExpression(hyperlinkUrl);
                            try {
                                field.setValue(formExpression.getValue(new VariableContainerWrapper(variables)));
                            } catch (Exception e) {
                                LOG.error("Error getting value for hyperlink expression {} {}", hyperlinkUrl, e.getMessage(), e);
                            }
                        }
                    }

                } else if (field instanceof ExpressionFormField) {
                    ExpressionFormField expressionField = (ExpressionFormField) field;
                    Expression formExpression = formEngineConfiguration.getExpressionManager().createExpression(expressionField.getExpression());
                    try {
                        field.setValue(formExpression.getValue(new VariableContainerWrapper(variables)));
                    } catch (Exception e) {
                        LOG.error("Error getting value for expression {} {}", expressionField.getExpression(), e.getMessage());
                    }

                } else if (FormFieldTypes.UPLOAD.equals(field.getType())) {

                    // Multiple docs are stored as comma-separated string ids,
                    // explicitly storing them as an array, so they're serialized properly
                    if (variables.containsKey(field.getId())) {
                        String uploadValue = (String) variables.get(field.getId());
                        if (uploadValue != null) {
                            List<String> contentIds = new ArrayList<>();
                            Collections.addAll(contentIds, uploadValue.split(","));
                            field.setValue(contentIds);
                        }
                    }

                } else {
                    Object variableValue = variables.get(field.getId());
                    if (variableValue != null) {

                        if (variableValue instanceof java.time.LocalDate) {
                            field.setValue(((java.time.LocalDate) variableValue).format(DateTimeFormatter.ISO_DATE));
                        } else if (variableValue instanceof Date) {
                            field.setValue(DATE_FORMAT.format(((Date) variableValue).toInstant()));
                        } else if (variableValue instanceof LocalDate dateVariable) {
                            field.setValue(dateVariable.toString("yyyy-M-d"));
                        } else {
                            field.setValue(variableValue);
                        }
                    }
                }

                field.setReadOnly(true);
            }
        }
    }

    protected void fillFormInstanceValues(FormInstanceInfo formInstanceModel, FormInstance formInstance,
                                          Map<String, JsonNode> formInstanceFieldMap, ObjectMapper objectMapper) {

        try {
            JsonNode submittedNode = objectMapper.readTree(formInstance.getFormValueBytes());
            if (submittedNode == null) {
                return;
            }

            if (submittedNode.get("values") != null) {
                JsonNode valuesNode = submittedNode.get("values");
                Iterator<String> fieldIdIterator = valuesNode.fieldNames();
                while (fieldIdIterator.hasNext()) {
                    String fieldId = fieldIdIterator.next();
                    JsonNode valueNode = valuesNode.get(fieldId);
                    formInstanceFieldMap.put(fieldId, valueNode);
                }
            }

            if (submittedNode.get("flowable_form_outcome") != null) {
                JsonNode outcomeNode = submittedNode.get("flowable_form_outcome");
                if (!outcomeNode.isNull() && StringUtils.isNotEmpty(outcomeNode.asText())) {
                    formInstanceModel.setSelectedOutcome(outcomeNode.asText());
                }
            }

        } catch (Exception e) {
            throw new FlowableException("Error parsing form instance " + formInstance.getId(), e);
        }
    }

    public void fillVariablesWithFormValues(Map<String, JsonNode> submittedFormFieldMap, List<FormField> allFields) {
        for (FormField field : allFields) {

            JsonNode fieldValueNode = submittedFormFieldMap.get(field.getId());

            if (fieldValueNode == null || fieldValueNode.isNull()) {
                continue;
            }

            String fieldType = field.getType();
            String fieldValue = fieldValueNode.asText();

            if (FieldTypes.datetime.name().equals(fieldType)) {
                try {
                    if (StringUtils.isNotEmpty(fieldValue)) {
                        LocalDate dateValue = LocalDate.parse(fieldValue);
                        variables.put(field.getId(), dateValue.toString("d-M-yyyy"));
                    }
                } catch (Exception e) {
                    LOG.error("Error parsing form date value for process instance {} and task {} with value {}", processInstanceId, taskId, fieldValue, e);
                }

            } else if (fieldValueNode.isBoolean()) {
                variables.put(field.getId(), fieldValueNode.asBoolean());

            } else if (fieldValueNode.isLong()) {
                variables.put(field.getId(), fieldValueNode.asLong());

            } else if (fieldValueNode.isDouble()) {
                variables.put(field.getId(), fieldValueNode.asDouble());

            } else {
                variables.put(field.getId(), fieldValue);
            }
        }
    }
}
