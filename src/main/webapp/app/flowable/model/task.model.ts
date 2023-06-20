import { DateTime } from 'luxon';
import { User } from './common.model';

export interface TaskInstance {
    id: string;
    name: string;
    owner?: string;
    created: DateTime;
    dueDate?: DateTime;
    endDate: DateTime;
    formKey?: string;
    priority?: number;
    parentTaskId?: string;
    caseInstanceId?: string;
    processInstanceId?: string;
    description: string;
    category: string;
    assignee?: User;
    duration: number;
    processInstanceName: string;
    processDefinitionId: string;
    processDefinitionName: string;
    processDefinitionDescription: string;
    processDefinitionKey: string;
    processDefinitionCategory: string;
    processDefinitionVersion: number;
    processDefinitionDeploymentId: string;
    scopeId: string;
    scopeType: string;
    caseInstanceName: string;
    scopeDefinitionId: string;
    caseDefinitionName: string;
    caseDefinitionDescription: string;
    caseDefinitionKey: string;
    caseDefinitionCategory: string;
    caseDefinitionVersion: number;
    caseDefinitionDeploymentId: string;
    parentTaskName: string;
    processInstanceStartUserId: string;
    initiatorCanCompleteTask: boolean;
    involvedPeople: User[];
    memberOfCandidateGroup: boolean;
    memberOfCandidateUsers: boolean
}
