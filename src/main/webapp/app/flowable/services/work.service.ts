import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { combineLatest, forkJoin, map, switchMap } from 'rxjs';
import { AppDefinition, WorkForm } from '../model/work.model';
import { ProcessService } from './process.service';
import { CaseService } from './case.service';
import { ListResult } from '../model/common.model';

@Injectable({
    providedIn: 'root'
})
export class WorkService {
    constructor(private http: HttpClient, private processService: ProcessService, private caseService: CaseService) {
    }

    getWorkForm() {
        return this.http.get<ListResult<AppDefinition>>('/api/rest/runtime/app-definitions').pipe(
            map((apps: ListResult<AppDefinition>) => apps.data),
            switchMap((apps: AppDefinition[]) => {
                const deployments = new Set<string>();
                const stream = apps.map(app => {
                    return forkJoin([
                        this.processService.listProcessDefinitions({
                            latest: true,
                            appDefinitionKey: app.appDefinitionKey
                        }),
                        this.caseService.listCaseDefinitions({latest: true, appDefinitionKey: app.appDefinitionKey})
                    ]).pipe(
                        map(([processDefinitions, caseDefinitions]) => {
                            const workForm: WorkForm = {
                                app: app,
                                definitions: []
                            };
                            workForm.definitions.push(
                                ...processDefinitions.data.map(p => {
                                    p.type = 'process';
                                    return p;
                                })
                            );
                            workForm.definitions.push(...
                                caseDefinitions.data.map(c => {
                                    c.type = 'case';
                                    return c;
                                })
                            );

                            deployments.add(workForm.definitions[0].deploymentId);

                            workForm.definitions = workForm.definitions.sort((d1, d2) => d1.name.localeCompare(d2.name))
                            return workForm;
                        })
                    )
                });
                stream.push(
                    forkJoin([
                        this.processService.listProcessDefinitions({
                            latest: true,
                        }),
                        this.caseService.listCaseDefinitions({latest: true,})
                    ]).pipe(
                        map(([processDefinitions, caseDefinitions]) => {
                            const workForm: WorkForm = {
                                app: {
                                    icon: "glyphicon-ok",
                                    name: "Unclassified",
                                    theme: "theme-8"
                                },
                                definitions: []
                            };
                            workForm.definitions.push(
                                ...processDefinitions.data.map(p => {
                                    p.type = 'process';
                                    return p;
                                })
                            );
                            workForm.definitions.push(...
                                caseDefinitions.data.map(c => {
                                    c.type = 'case';
                                    return c;
                                })
                            );

                            workForm.definitions = workForm.definitions.filter(d => !deployments.has(d.deploymentId))
                                .sort((d1, d2) => d1.name.localeCompare(d2.name))
                            return workForm;
                        })
                    )
                );
                return combineLatest(stream)
            })
        );
    }

}
