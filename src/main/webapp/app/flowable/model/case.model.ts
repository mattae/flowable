import { DateTime } from 'luxon';
import { RestVariable, SaveForm, User } from './common.model';
import { DefinitionType } from './work.model';

export interface CaseInstance {
    id: string;
    name: string;
    businessKey: string;
    started: DateTime;
    startedBy: User;
    ended: DateTime;
    caseDefinitionId: string;
    caseDefinitionName: string;
    caseDefinitionDescription: string;
    caseDefinitionCategory: string;
    caseDefinitionKey: string;
    caseDefinitionVersion: number;
    caseDefinitionDeploymentId: string;
    startFormDefined: boolean;
    graphicalNotationDefined: boolean;
    tenantId: string
    variables: RestVariable[];
}

export interface CaseDefinition {
    id: string;
    category: string;
    name: string;
    key: string;
    description: string;
    version: number;
    resourceName: string;
    deploymentId: string;
    diagramResourceName: string
    hasStartForm: boolean;
    hasGraphicalNotation: boolean;
    tenantId: string;
    type: DefinitionType;
}

export interface StageRepresentation {
    name: string;
    state: string;
    startedDate: DateTime;
    endedDate: DateTime;
}

export interface MilestoneRepresentation {
    name: string;
    state: string;
    timestamp: DateTime;
}

export interface PlanItemRepresentation {
    id: string;
    caseDefinitionId: string;
    caseInstanceId: string;
    stageInstanceId: string;
    isStage: boolean;
    elementId: string;
    planItemDefinitionId: string;
    planItemDefinitionType: string;
    name: string;
    state: string;
    createTime: DateTime;
}

export interface CreateCaseForm extends SaveForm {
    caseDefinitionId: string;
    name?: string;
}
