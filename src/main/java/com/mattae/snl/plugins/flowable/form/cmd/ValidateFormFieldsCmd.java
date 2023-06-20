// 
// Decompiled by Procyon v0.5.36
// 

package com.mattae.snl.plugins.flowable.form.cmd;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.form.api.FormInfo;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ValidateFormFieldsCmd implements Command<Void> {
    protected FormInfo formInfo;
    protected Map<String, Object> values;

    public ValidateFormFieldsCmd(final FormInfo formInfo, final Map<String, Object> values) {
        this.formInfo = formInfo;
        this.values = values;
    }

    public Void execute(final CommandContext commandContext) {

        return null;
    }

    protected Map<String, Object> getNonSystemVariables(final Map<String, Object> values, final String outcomeVariableName) {
        final Map<String, Object> filteredVariables = new HashMap<String, Object>(values);
        for (final String varName : values.keySet()) {
            if (varName.startsWith("_") || varName.equals("initiator") || this.isOutcomeVariable(varName, outcomeVariableName) || this.isPayload(varName)) {
                filteredVariables.remove(varName);
            }
        }
        return filteredVariables;
    }

    protected boolean isPayload(final String variableName) {
        return "flowableDatabasePayload".equals(variableName) || "flowablePayloadHash".equals(variableName) || "flowablePayloadTime".equals(variableName);
    }

    protected boolean isOutcomeVariable(final String variableName, final String outcomeVariableName) {
        return (variableName.startsWith("form_") && variableName.endsWith("_outcome")) || (StringUtils.isEmpty((CharSequence) outcomeVariableName) ? variableName.equals("outcome") : variableName.equals(outcomeVariableName));
    }
}