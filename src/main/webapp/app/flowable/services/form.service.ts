import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormDefinition, FormDeployment } from '../model/form.model';
import { ListResult } from '../model/common.model';
import { map } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class FormService {

    constructor(private http: HttpClient) {
    }

    getFormDefinitions() {
        return this.http.get<ListResult<FormDefinition>>('/api/rest/form-repository/form-definitions');
    }

    saveFormDefinition(formData: FormData) {
        return this.http.post<FormDeployment>('/api/rest/form-repository/deployments', formData)
    }

    getFormData(payload: {
        formDefinitionKey: string,
        taskId?: string,
        processInstanceId?: string,
        caseInstanceId?: string
    }) {
        return this.http.post<{ [key: string]: any; }>('/api/rest/form/form-instance-model', payload).pipe(
            map(res => {
                const result: { [key: string]: any; } = {};
                res.fields.forEach(field => {
                    if (field['key']) {
                        result[field['key']] = field['value'];
                    }
                });
                return result;
            })
        );
    }
}
