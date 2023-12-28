import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Group, User } from '../model/common.model';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private resourceUrl = '/api/rest';

    constructor(private http: HttpClient) {
    }

    getUser(userId: string) {
        return this.http.get<User>(`${this.resourceUrl}/users/${userId}`);
    }

    getWorkflowUsers(filter: {
        filter?: string,
        excludeTaskId?: string,
        excludeProcessId?: string,
        excludeCaseId?: string
    }) {
        const params = new HttpParams()
            .set('filter', filter.filter ?? '')
            .set('excludeTaskId', filter.excludeTaskId || null)
            .set('excludeProcessId', filter.excludeProcessId || null)
            .set('excludeCaseId', filter.excludeCaseId || null);

        return this.http.get<User[]>(`${this.resourceUrl}/workflow-users`, {params});
    }

    getGroup(groupId: string) {
        return this.http.get<Group>(`${this.resourceUrl}/workflow-groups/${groupId}`);
    }

    getWorkflowGroups(filter?: string) {
        const params = new HttpParams()
            .set('filter', filter ?? '')
        return this.http.get<Group[]>(`${this.resourceUrl}/workflow-groups`)
    }
}
