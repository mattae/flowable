import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DateTime } from 'luxon';
import { TaskInstance } from '../model/task.model';
import { map, Observable } from 'rxjs';
import { CompleteForm, FormModel, ListResult, SaveForm, User } from '../model/common.model';

@Injectable({
    providedIn: 'root'
})
export class TaskService {
    private resourceUrl = '/api/rest/tasks';

    constructor(private http: HttpClient) {
    }

    getTask(taskId: string): Observable<TaskInstance> {
        return this.http.get<TaskInstance>(`${this.resourceUrl}/${taskId}`).pipe(
            map(res => this.mapTaskFromServer(res))
        );
    }

    getSubTasks(taskId: string): Observable<TaskInstance[]> {
        return this.http.get<TaskInstance[]>(`${this.resourceUrl}/${taskId}/subtasks`).pipe(
            map(res => res.map(t => this.mapTaskFromServer(t)))
        );
    }

    createTask(taskRepresentation: {
        name: string;
        description: string;
        category?: string;
        assignee?: string;
        parentTaskId?: string;
    }): Observable<TaskInstance> {
        return this.http.post<TaskInstance>(this.resourceUrl, taskRepresentation).pipe(
            map(res => this.mapTaskFromServer(res))
        );
    }

    updateTask(taskId: string, update: {
        name?: string;
        description?: string;
        dueDate?: DateTime;
        formKey?: string;
    }): Observable<TaskInstance> {
        update = Object.assign({}, update, {
            dueDate: update.dueDate != null ? update.dueDate.toISO({includeOffset: false}) : null
        });
        if (!update.name) {
            delete update.name;
        } else {
            update['nameSet'] = true;
        }
        if (!update.description) {
            delete update.description;
        } else {
            update['descriptionSet'] = true;
        }
        if (!update.formKey) {
            delete update.formKey;
        } else {
            update['formKeySet'] = true;
        }
        if (!update.dueDate) {
            delete update.dueDate;
        } else {
            update['dueDateSet'] = true;
        }
        return this.http.put<TaskInstance>(`${this.resourceUrl}/${taskId}`, update);
    }

    completeTask(taskId: string) {
        return this.http.put<void>(`${this.resourceUrl}/${taskId}/action/complete`, {});
    }

    claimTask(taskId: string) {
        return this.http.put<void>(`${this.resourceUrl}/${taskId}/action/claim`, {});
    }

    unClaimTask(taskId: string) {
        return this.http.put<void>(`${this.resourceUrl}/${taskId}/action/un-claim`, {});
    }

    assignTask(taskId: string, assignee: string) {
        return this.http.put<TaskInstance>(`${this.resourceUrl}/${taskId}/action/assign`, {assignee: assignee}).pipe(
            map(res => this.mapTaskFromServer(res))
        );
        ;
    }

    involveTask(taskId: string, userId: string) {
        return this.http.put<void>(`${this.resourceUrl}/${taskId}/action/involve`, {userId: userId});
    }

    removeInvolveTask(taskId: string, payload: { userId?: string; email?: string }) {
        return this.http.put<void>(`${this.resourceUrl}/${taskId}/action/remove-involve`, payload);
    }

    getInvolvedUsers(taskId: string) {
        return this.http.get<User[]>(`${this.resourceUrl}/tasks/${taskId}/involved-users`);
    }

    getTaskForm(taskId: string) {
        return this.http.get<FormModel>(`/api/rest/task-forms/${taskId}`).pipe(
            map(res => {
                    res.components = res.components.filter(cmp => {
                        return cmp.key !== 'submit' || cmp.action !== 'submit'
                    })
                    return res;
                }
            )
        );
    }

    completeTaskForm(taskId: string, payload: CompleteForm) {
        return this.http.post<void>(`/api/rest/task-forms/${taskId}`, payload);
    }

    saveTaskForm(taskId: string, payload: SaveForm) {
        return this.http.post<void>(`/api/rest/task-forms/${taskId}/save-form`, payload);
    }

    listTasks(payload: {
        state?: string;
        appDefinitionKey?: string;
        processInstanceId?: string;
        caseInstanceId?: string;
        text?: string;
        assignment?: string;
        processDefinitionId?: string;
        dueBefore?: string;
        dueAfter?: string;
        sort?: string;
        page?: number;
        size?: number;
    }) {
        return this.http.post<ListResult<TaskInstance>>(`/api/rest/query/tasks`, payload).pipe(
            map(res => {
                res.data = res?.data.map(d => this.mapTaskFromServer(d));
                return res;
            })
        );
    }

    private mapTaskFromServer(task: TaskInstance): TaskInstance {
        return Object.assign({}, task, {
            created: task.created ? DateTime.fromISO(task.created as unknown as string) : null,
            dueDate: task.dueDate ? DateTime.fromISO(task.dueDate as unknown as string) : null,
            endDate: task.endDate ? DateTime.fromISO(task.endDate as unknown as string) : null
        })
    }
}
