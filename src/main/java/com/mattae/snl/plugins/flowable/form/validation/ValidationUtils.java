// 
// Decompiled by Procyon v0.5.36
// 

package com.mattae.snl.plugins.flowable.form.validation;

public abstract class ValidationUtils {

    public static String createVariableNamePrefix(final String variableNamePrefix, final String variableName) {
        if (variableNamePrefix.isEmpty()) {
            return variableName + ".";
        }
        if (!variableNamePrefix.endsWith(".")) {
            return variableNamePrefix + "." + variableName + ".";
        }
        return variableNamePrefix + variableName + ".";
    }
}
