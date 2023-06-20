package com.mattae.snl.plugins.flowable.services;

import lombok.RequiredArgsConstructor;
import org.flowable.cmmn.api.CmmnRuntimeService;
import org.flowable.engine.ProcessEngine;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CmmnService {
    private final ProcessEngine processEngine;
    private final CmmnRuntimeService cmmnRuntimeService;

    public List<Task> getOpenTasks(String caseInstanceId) {
        processEngine.getFormService().getRenderedStartForm(caseInstanceId);
        return processEngine.getTaskService().createTaskQuery()
                .caseInstanceId(caseInstanceId)
                .active()
                .orderByTaskName()
                .asc()
                .list();
    }
}
