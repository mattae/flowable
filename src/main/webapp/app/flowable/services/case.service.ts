import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { FormModel, ListResult } from '../model/common.model';
import { map } from 'rxjs';
import { DateTime } from 'luxon';
import {
    CaseDefinition,
    CaseInstance,
    CreateCaseForm,
    MilestoneRepresentation,
    PlanItemRepresentation,
    StageRepresentation
} from '../model/case.model';

@Injectable({
    providedIn: 'root'
})
export class CaseService {
    private readonly resourceUrl = '/app/rest';

    constructor(private http: HttpClient) {
    }

    getCaseDefinitionStartForm(caseDefinitionId: string) {
        return this.http.get<FormModel>(`${this.resourceUrl}/case-definitions/${caseDefinitionId}/start-form}`);
    }

    listCaseDefinitions(request: { latest?: boolean; appDefinitionKey?: string }) {
        let params = new HttpParams()
            .set('latest', request.latest ?? true);
        if (request.appDefinitionKey) {
            params = params.append('appDefinitionKey', request.appDefinitionKey);
        }
        return this.http.get<ListResult<CaseDefinition>>(`${this.resourceUrl}/case-definitions`, {params})
    }

    getCaseInstance(caseInstanceId: string) {
        return this.http.get<CaseInstance>(`${this.resourceUrl}/case-instances/${caseInstanceId}`).pipe(
            map(res => this.convertCaseFromServer(res))
        );
    }

    listCaseInstances(request: {
        state?: string;
        appDefinitionKey?: string;
        caseDefinitionId?: string;
        sort?: string;
        page?: number;
        size?: number
    }) {
        return this.http.post<ListResult<CaseInstance>>(`${this.resourceUrl}/query/case-instances`, request).pipe(
            map(res => {
                res.data = res.data.map(p => this.convertCaseFromServer(p))
                return res;
            })
        )
    }

    getCaseInstanceStartForm(caseInstanceId: string) {
        return this.http.get<FormModel>(`${this.resourceUrl}/case-instances/${caseInstanceId}/start-form`);
    }

    deleteCaseInstance(caseInstanceId: string) {
        return this.http.delete(`${this.resourceUrl}/case-instances/${caseInstanceId}`);
    }

    createCaseInstance(caseForm: CreateCaseForm) {
        return this.http.post<CaseInstance>(`${this.resourceUrl}/case-instances`, caseForm);
    }

    activeStages(caseInstanceId: string) {
        return this.http.get<ListResult<StageRepresentation>>(`${this.resourceUrl}/case-instances/${caseInstanceId}/active-stages`)
            .pipe(map(res => {
                res.data = res.data.map(p => this.convertStageFromServer(p))
                return res;
            }));
    }

    endedStages(caseInstanceId: string) {
        return this.http.get<ListResult<StageRepresentation>>(`${this.resourceUrl}/case-instances/${caseInstanceId}/ended-stages`)
            .pipe(map(res => {
                res.data = res.data.map(p => this.convertStageFromServer(p))
                return res;
            }));
    }

    availableMilestones(caseInstanceId: string) {
        return this.http.get<ListResult<MilestoneRepresentation>>(`${this.resourceUrl}/case-instances/${caseInstanceId}/available-milestones`)
            .pipe(map(res => {
                res.data = res.data.map(p => this.convertMilestoneFromServer(p))
                return res;
            }));
    }

    enabledPlanItems(caseInstanceId: string) {
        return this.http.get<ListResult<PlanItemRepresentation>>(`${this.resourceUrl}/case-instances/${caseInstanceId}/enabled-planitem-instances`)
            .pipe(map(res => {
                res.data = res.data.map(p => this.convertPlanItemFromServer(p))
                return res;
            }));
    }

    startPlanItem(caseInstanceId: string, planItemInstanceId: string) {
        return this.http.get<void>(`${this.resourceUrl}/case-instances/${caseInstanceId}/enabled-planitem-instances/${planItemInstanceId}`);
    }

    endedMilestones(caseInstanceId: string) {
        return this.http.get<ListResult<MilestoneRepresentation>>(`${this.resourceUrl}/case-instances/${caseInstanceId}/ended-milestones`)
            .pipe(map(res => {
                res.data = res.data.map(p => this.convertMilestoneFromServer(p))
                return res;
            }));
    }

    private convertCaseFromServer(caseInstance: CaseInstance) {
        return Object.assign({}, caseInstance, {
            started: DateTime.fromISO(caseInstance.started as unknown as string),
            ended: caseInstance.ended ? DateTime.fromISO(caseInstance.ended as unknown as string) : null
        })
    }

    private convertStageFromServer(stage: StageRepresentation) {
        return Object.assign({}, stage, {
            started: stage.startedDate ? DateTime.fromISO(stage.startedDate as unknown as string) : null,
            ended: stage.endedDate ? DateTime.fromISO(stage.endedDate as unknown as string) : null
        });
    }

    private convertMilestoneFromServer(milestone: MilestoneRepresentation) {
        return Object.assign({}, milestone, {
            timestamp: milestone.timestamp ? DateTime.fromISO(milestone.timestamp as unknown as string) : null
        });
    }

    private convertPlanItemFromServer(planItem: PlanItemRepresentation) {
        return Object.assign({}, planItem, {
            createTime: planItem.createTime ? DateTime.fromISO(planItem.createTime as unknown as string) : null
        });
    }
}
