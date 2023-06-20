import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class TutorialService {
    private resourceUrl = '/api/tutorials';

    constructor(private http: HttpClient) {
    }

    getTutorials(): Observable<any[]> {
        return this.http.get<any[]>(this.resourceUrl);
    }

    updateTutorial(tutorial: any): Observable<any> {
        return this.http.put<any>(this.resourceUrl, tutorial);
    }

    saveTutorial(tutorial: any): Observable<any> {
        return this.http.post<any>(this.resourceUrl, tutorial);
    }

    deleteTutorial(id: number): Observable<any> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`);
    }

    deleteAllTutorials(): Observable<any> {
        return this.http.delete<any>(`${this.resourceUrl}`);
    }

    getTutorial(id: any): Observable<any> {
        return this.http.get<any>(`${this.resourceUrl}/${id}`);
    }

    getTutorialsByTitle(title: string): Observable<any[]> {
        return this.http.get<any[]>(`${this.resourceUrl}/title?title=${title}`);
    }
}
