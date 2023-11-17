import { DateTime } from 'luxon';
import { CompleteForm, RestVariable, User } from './common.model';
import { DefinitionType } from './work.model';

export interface CreateProcessForm extends CompleteForm {
    processDefinitionId: string;
    name?: string;
}

export interface ProcessInstance {
    id: string;
    name: string;
    businessKey: string;
    businessStatus: string;
    suspended: boolean;
    ended: DateTime;
    processDefinitionId: boolean;
    startedBy: User;
    started: DateTime;
    completed: boolean;
    tenantId: string;
    processDefinitionName: string;
    processDefinitionVersion: string;
    processDefinitionDescription: string;
    processDefinitionKey: string;
    processDefinitionCategory: string;
    processDefinitionDeploymentId: string;
    graphicalNotationDefined: boolean;
    startFormDefined: boolean;
    superProcessInstanceId: string;
    variables: RestVariable[];

}

export interface ProcessDefinition {
    id: string;
    category: string;
    name: string;
    key: string;
    description: string;
    version: number;
    resourceName: string;
    deploymentId: string;
    diagramResourceName: string;
    hasStartForm: boolean;
    hasGraphicalNotation: boolean;
    isSuspended: boolean;
    tenantId: string;
    derivedFrom: string;
    derivedFromRoot: string;
    derivedVersion: number;
    engineVersion: string;
    type: DefinitionType;
}
