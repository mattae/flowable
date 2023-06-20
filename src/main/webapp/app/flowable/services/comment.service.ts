import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ListResult } from '../model/common.model';
import { Comment } from '../model/comment.model';
import { DateTime } from 'luxon';
import { map } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class CommentService {
    private resourceUrl = '/app/rest';

    constructor(private http: HttpClient) {
    }

    getTaskComments(taskId: string) {
        return this.http.get<ListResult<Comment>>(`${this.resourceUrl}/tasks/${taskId}/comments`).pipe(
            map(res => {
                res.data = this.convertFromServer(res.data);
                return res;
            })
        );
    }

    addTaskComment(taskId: string, comment: Comment) {
        return this.http.post<Comment>(`${this.resourceUrl}/tasks/${taskId}/comments`, comment).pipe(
            map(res => this.convertCommentFromServer(res))
        );
    }

    getProcessComments(processInstanceId: string) {
        return this.http.get<ListResult<Comment>>(`${this.resourceUrl}/process-instances/${processInstanceId}/comments`).pipe(
            map(res => {
                res.data = this.convertFromServer(res.data);
                return res;
            })
        );
        ;
    }

    addProcessComment(processInstanceId: string, comment: Comment) {
        return this.http.post<Comment>(`${this.resourceUrl}/process-instances/${processInstanceId}/comments`, comment).pipe(
            map(res => this.convertCommentFromServer(res))
        );
        ;
    }

    getCaseComments(caseInstanceId: string) {
        return this.http.get<ListResult<Comment>>(`${this.resourceUrl}/case-instances/${caseInstanceId}/comments`).pipe(
            map(res => {
                res.data = this.convertFromServer(res.data);
                return res;
            })
        );
        ;
    }

    addCaseComment(caseInstanceId: string, comment: Comment) {
        return this.http.post<Comment>(`${this.resourceUrl}/case-instances/${caseInstanceId}/comments`, comment).pipe(
            map(res => this.convertCommentFromServer(res))
        );
        ;
    }

    private convertFromServer(comments: Comment[]) {
        return comments.map(comment => {
                comment = this.convertCommentFromServer(comment);
                return comment;
            }
        );
    }

    private convertCommentFromServer(comment: Comment) {
        comment.created = DateTime.fromISO(comment.created as unknown as string);
        return comment;
    }
}
