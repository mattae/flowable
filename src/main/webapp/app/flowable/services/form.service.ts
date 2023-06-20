import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormDefinition, FormDeployment } from '../model/form.model';
import { ListResult } from '../model/common.model';

@Injectable({
    providedIn: 'root'
})
export class FormService {

    constructor(private http: HttpClient) {
    }

    getFormDefinitions() {
        return this.http.get<ListResult<FormDefinition>>('/app/rest/form-repository/form-definitions');
    }

    saveFormDefinition(formData: FormData) {
        return this.http.post<FormDeployment>('/app/rest/form-repository/deployments', formData)
    }
}
