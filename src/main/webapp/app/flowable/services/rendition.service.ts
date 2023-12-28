import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class RenditionService {
    private resourceUrl = '/api/renditions';

    constructor(private http: HttpClient) {
    }

    getRawRenditionItem(renditionItemId: string) {
        return this.http.get(`${this.resourceUrl}/rendition-items/${renditionItemId}/data`,{  responseType: 'blob',  observe: 'response'});
    }
}
