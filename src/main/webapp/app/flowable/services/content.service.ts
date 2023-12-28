import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ListResult } from '../model/common.model';
import { Content } from '../model/content.model';
import { DateTime } from 'luxon';
import { map } from 'rxjs';
import { upload } from 'ngx-operators';

@Injectable({
    providedIn: 'root'
})
export class ContentService {
    private resourceUrl = '/api/rest';

    constructor(private http: HttpClient) {
    }

    getContentItemsForTask(taskId: string) {
        return this.http.get<ListResult<Content>>(`${this.resourceUrl}/tasks/${taskId}/content`).pipe(
            map(res => {
                res.data = this.convertFromServer(res.data);
                return res;
            })
        );
    }

    getContentItemsForProcessInstance(processInstanceId: string) {
        return this.http.get<ListResult<Content>>(`${this.resourceUrl}/process-instances/${processInstanceId}/content`).pipe(
            map(res => {
                res.data = this.convertFromServer(res.data);
                return res;
            })
        );
    }

    getContentItemsForCaseInstance(caseInstanceId: string) {
        return this.http.get<ListResult<Content>>(`${this.resourceUrl}/case-instances/${caseInstanceId}/content`).pipe(
            map(res => {
                res.data = this.convertFromServer(res.data);
                return res;
            })
        );
    }

    createContentItemOnTask(taskId: string, content: Content) {
        return this.http.post<Content>(`${this.resourceUrl}/tasks/${taskId}/content`, content).pipe(
            map(res => this.convertContentFromServer(res))
        );
    }

    createRawContentItemOnTask(taskId: string, formData: FormData) {
        return this.http.post<Content>(`${this.resourceUrl}/tasks/${taskId}/raw-content`, formData, {
            reportProgress: true,
            observe: 'events'
        }).pipe(
            upload(),
            map(res => {
                res.body = this.convertContentFromServer(res.body);
                return res;
            })
        );
    }

    createContentItemOnProcessInstance(processInstanceId: string, content: Content) {
        return this.http.post<Content>(`${this.resourceUrl}/processes/${processInstanceId}/content`, content).pipe(
            map(res => this.convertContentFromServer(res))
        );
    }

    createRawContentItemOnProcessInstance(processInstanceId: string, content: FormData) {
        return this.http.post<Content>(`${this.resourceUrl}/process-instances/${processInstanceId}/raw-content`, content, {
            reportProgress: true,
            observe: 'events'
        }).pipe(
            upload(),
            map(res => {
                res.body = this.convertContentFromServer(res.body);
                return res;
            })
        );
    }

    createRawContentItemOnCaseInstance(caseInstanceId: string, content: FormData) {
        return this.http.post<Content>(`${this.resourceUrl}/case-instances/${caseInstanceId}/raw-content`, content, {
            reportProgress: true,
            observe: 'events'
        }).pipe(
            upload(),
            map(res => {
                res.body = this.convertContentFromServer(res.body);
                return res;
            })
        );
    }

    createTemporaryRawContentItem(formData: FormData) {
        return this.http.post<Content>(`${this.resourceUrl}/content/raw`, formData, {
            reportProgress: true,
            observe: 'events'
        }).pipe(
            upload(),
            map(res => {
                res.body = this.convertContentFromServer(res.body);
                return res;
            })
        );
    }

    deleteContentItem(contentId: string) {
        return this.http.delete<void>(`${this.resourceUrl}/content/${contentId}`);
    }

    getContentItem(contentId: string) {
        return this.http.get<Content>(`${this.resourceUrl}/content/${contentId}`);
    }

    getRawContentItem(contentId: string) {
        return this.http.get(`${this.resourceUrl}/content/${contentId}/raw`, {
            responseType: 'blob',
            observe: 'response'
        });
    }

    private convertFromServer(contents: Content[]) {
        return contents.map(content => {
                content = this.convertContentFromServer(content);
                return content;
            }
        );
    }

    private convertContentFromServer(content: Content) {
        content.created = DateTime.fromISO(content.created as unknown as string);
        content.lastModified = DateTime.fromISO(content.lastModified as unknown as string);
        return content;
    }
}
