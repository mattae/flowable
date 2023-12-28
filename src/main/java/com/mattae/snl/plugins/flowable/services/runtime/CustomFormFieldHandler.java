package com.mattae.snl.plugins.flowable.services.runtime;

import org.apache.commons.lang3.StringUtils;
import org.flowable.cmmn.engine.impl.util.CommandContextUtil;
import org.flowable.content.api.ContentItem;
import org.flowable.content.api.ContentService;
import org.flowable.engine.impl.formhandler.DefaultFormFieldHandler;
import org.flowable.form.api.FormInfo;
import org.flowable.form.model.FormField;
import org.flowable.form.model.FormFieldTypes;
import org.flowable.form.model.SimpleFormModel;

import java.util.*;

public class CustomFormFieldHandler extends DefaultFormFieldHandler {
    @Override
    public void handleFormFieldsOnSubmit(FormInfo formInfo, String taskId, String processInstanceId, String scopeId,
                                         String scopeType, Map<String, Object> variables, String tenantId) {
        ContentService contentService = CommandContextUtil.getContentService();
        if (contentService == null || formInfo == null) {
            return;
        }

        SimpleFormModel formModel = (SimpleFormModel) formInfo.getFormModel();
        if (formModel != null && formModel.getFields() != null) {
            for (FormField formField : formModel.getFields()) {
                if (FormFieldTypes.UPLOAD.equals(formField.getType())) {

                    String variableName = formField.getId();
                    if (variables.containsKey(variableName)) {
                        String variableValue = (String) variables.get(variableName);
                        if (StringUtils.isNotEmpty(variableValue)) {
                            String[] contentItemIds = StringUtils.split(variableValue, ",");
                            Set<String> contentItemIdSet = new HashSet<>();
                            Collections.addAll(contentItemIdSet, contentItemIds);

                            List<ContentItem> contentItems = contentService.createContentItemQuery().ids(contentItemIdSet).list();

                            for (ContentItem contentItem : contentItems) {
                                contentItem.setTaskId(taskId);
                                contentItem.setProcessInstanceId(processInstanceId);
                                contentItem.setScopeId(scopeId);
                                contentItem.setScopeType(scopeType);
                                contentItem.setField(formField.getId());
                                contentItem.setTenantId(tenantId);
                                contentService.saveContentItem(contentItem);
                            }
                        }
                    }
                }
            }
        }
    }

}
