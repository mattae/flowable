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
package com.mattae.snl.plugins.flowable.form.impl.cmd;

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
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormInstance;
import org.flowable.form.engine.FormEngineConfiguration;
import org.flowable.form.engine.impl.util.CommandContextUtil;
import org.flowable.form.model.ExpressionFormField;
import org.flowable.form.model.FormField;
import org.flowable.form.model.FormFieldTypes;
import org.joda.time.LocalDate;

import java.time.Instant;
import java.util.*;

/**
 * @author Tijs Rademakers
 */
@Slf4j
public class GetFormModelWithVariablesCmd extends org.flowable.form.engine.impl.cmd.GetFormModelWithVariablesCmd {

    public GetFormModelWithVariablesCmd(String formDefinitionKey, String formDefinitionId, String taskId, Map<String, Object> variables) {
        super(formDefinitionKey, formDefinitionId, taskId, variables);
    }

    public GetFormModelWithVariablesCmd(String formDefinitionKey, String parentDeploymentId, String formDefinitionId, String taskId, Map<String, Object> variables) {
        super(formDefinitionKey, parentDeploymentId, formDefinitionId, taskId, variables);
    }

    public GetFormModelWithVariablesCmd(String formDefinitionKey, String parentDeploymentId, String formDefinitionId, String taskId, String tenantId, Map<String, Object> variables, boolean fallbackToDefaultTenant) {
        super(formDefinitionKey, parentDeploymentId, formDefinitionId, taskId, tenantId, variables, fallbackToDefaultTenant);
    }

    protected void fillFormFieldValues(FormInstance formInstance, FormInfo formInfo, CommandContext commandContext) {

        FormEngineConfiguration formEngineConfiguration = CommandContextUtil.getFormEngineConfiguration();
        FormIOFormModel formModel = (FormIOFormModel) formInfo.getFormModel();
        List<FormField> allFields = formModel.listAllFields();
        if (allFields != null) {

            Map<String, JsonNode> formInstanceFieldMap = new HashMap<>();
            if (formInstance != null) {
                fillFormInstanceValues(formInstance, formInstanceFieldMap, formEngineConfiguration.getObjectMapper());
                fillVariablesWithFormInstanceValues(formInstanceFieldMap, allFields, formInstance.getId());
            }

            for (FormField field : allFields) {
                if (field instanceof FormIOFormModel.Select optionFormField) {
                    // Drop down options to be populated from an expression
                    /*if (optionFormField.getValueProperty() != null && optionFormField.getValueProperty().equals("json")) {
                        List<FormIOFormModel.Option> value = null;
                        try {
                            value = (List<FormIOFormModel.Option>) variables.get("");
                        } catch (Exception e) {
                            throw new FlowableException("Error getting value for json data: " + optionFormField.getTemplate(), e);
                        }
                        if (value != null) {
                            FormIOFormModel.SelectData selectData = new FormIOFormModel.SelectData();
                            selectData.setValues(value);
                            optionFormField.setData(selectData);
                        } else {
                            throw new FlowableException("Invalid type from evaluated expression for optionsExpression: " + optionFormField.getData().getJson() + ", resulting type:" + value.getClass().getName());
                        }
                    }*/
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
                        LOG.error("Error getting value for expression {} {}", expressionField.getExpression(), e.getMessage(), e);
                    }

                } else {
                    Object variableValue = variables.get(field.getId());

                    if (variableValue != null) {
                        if (variableValue instanceof LocalDate) {
                            LocalDate dateVariable = (LocalDate) variableValue;
                            field.setValue(dateVariable.toString("yyyy-M-d"));

                        } else if (variableValue instanceof Date) {
                            Instant dateVariable = ((Date) variableValue).toInstant();
                            field.setValue(DATE_FORMAT.format(dateVariable));

                        } else if (variableValue instanceof java.time.LocalDate) {
                            java.time.LocalDate dateVariable = (java.time.LocalDate) variableValue;
                            field.setValue(DATE_FORMAT.format(dateVariable));

                        } else {
                            field.setValue(variableValue);
                        }
                    }
                }
            }
        }
    }

    protected void fillFormInstanceValues(
        FormInstance formInstance, Map<String, JsonNode> formInstanceFieldMap, ObjectMapper objectMapper) {

        if (formInstance == null) {
            return;
        }

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

        } catch (Exception e) {
            throw new FlowableException("Error parsing form instance " + formInstance.getId(), e);
        }
    }

    public void fillVariablesWithFormInstanceValues(Map<String, JsonNode> formInstanceFieldMap, List<FormField> allFields, String formInstanceId) {
        for (FormField field : allFields) {
            FormIOFormModel.Component formField = (FormIOFormModel.Component) field;
            JsonNode fieldValueNode = formInstanceFieldMap.get(field.getId());
            if (fieldValueNode == null || fieldValueNode.isNull()) {
                continue;
            }

            String fieldType = field.getType();
            String fieldValue = fieldValueNode.asText();

            if (FieldTypes.datetime.name().equals(fieldType)) {
                try {
                    if (StringUtils.isNotEmpty(fieldValue)) {
                        LocalDate dateValue = LocalDate.parse(fieldValue);
                        variables.put(field.getId(), dateValue.toString("yyyy-M-d"));
                    }

                } catch (Exception e) {
                    LOG.error("Error parsing form date value for form instance {} with value {}", formInstanceId, fieldValue, e);
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
