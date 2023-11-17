import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CreateProcessForm, ProcessDefinition, ProcessInstance } from '../model/process.model';
import { FormModel, ListResult, User } from '../model/common.model';
import { DateTime } from 'luxon';
import { map } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ProcessService {
    private resourceUrl = '/api/rest'

    constructor(private http: HttpClient) {
    }

    getProcessDefinitionStartForm(processDefinitionId: string) {
        return this.http.get<FormModel>(`${this.resourceUrl}/process-definitions/${processDefinitionId}/start-form}`).pipe(
            map(res => {
                    res.components = res.components.filter(cmp => {
                        return cmp.key !== 'submit' || cmp.action !== 'submit'
                    })
                    return res;
                }
            )
        );
    }

    listProcessDefinitions(request: { latest?: boolean; appDefinitionKey?: string }) {
        let params = new HttpParams()
            .set('latest', request.latest ?? true);
        if (request.appDefinitionKey) {
            params = params.append('appDefinitionKey', request.appDefinitionKey);
        }
        return this.http.get<ListResult<ProcessDefinition>>(`${this.resourceUrl}/process-definitions`, {params})
    }

    getProcessInstance(processInstanceId: string) {
        return this.http.get<ProcessInstance>(`${this.resourceUrl}/process-instances/${processInstanceId}`).pipe(
            map(res => this.convertFromServer(res))
        );
    }

    listProcessInstances(request: {
        state?: string;
        appDefinitionKey?: string;
        processDefinitionId?: string;
        sort?: string;
        page?: number;
        size?: number
    }) {
        return this.http.post<ListResult<ProcessInstance>>(`${this.resourceUrl}/query/process-instances`, request).pipe(
            map(res => {
                res.data = res.data.map(p => this.convertFromServer(p))
                return res;
            })
        )
    }

    getProcessInstanceStartForm(processInstanceId: string) {
        return this.http.get<FormModel>(`${this.resourceUrl}/process-instances/${processInstanceId}/start-form`);
    }

    deleteProcessInstance(processInstanceId: string) {
        return this.http.delete(`${this.resourceUrl}/process-instances/${processInstanceId}`);
    }

    createProcessInstance(processForm: CreateProcessForm) {
        return this.http.post<ProcessInstance>(`${this.resourceUrl}/process-instances`, processForm);
    }

    involveProcess(processInstanceId: string, userId: string) {
        return this.http.put<void>(`${this.resourceUrl}/process-instances/${processInstanceId}/action/involve`, {userId: userId});
    }

    removeInvolveProcess(processInstanceId: string, payload: { userId?: string; email?: string }) {
        return this.http.put<void>(`${this.resourceUrl}/process-instances/${processInstanceId}/action/remove-involve`, payload);
    }

    getInvolvedUsers(processInstanceId: string) {
        return this.http.get<User[]>(`${this.resourceUrl}/process-instances/${processInstanceId}/involved-users`);
    }

    private convertFromServer(processInstance: ProcessInstance) {
        return Object.assign({}, processInstance, {
            started: DateTime.fromISO(processInstance.started as unknown as string),
            ended: processInstance.ended ? DateTime.fromISO(processInstance.ended as unknown as string) : null
        })
    }
}
