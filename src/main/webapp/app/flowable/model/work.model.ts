import { ProcessDefinition } from './process.model';
import { CaseDefinition } from './case.model';

export interface AppDefinition {
    defaultAppId?: string;
    name: string;
    description?: string;
    theme: string;
    icon: string;
    appDefinitionId?: string;
    appDefinitionKey?: string;
    tenantId?: string;
}

export type Definition = CaseDefinition | ProcessDefinition;
export type DefinitionType = 'process' | 'case';

export interface WorkForm {
    app: AppDefinition;
    definitions?: Definition[];
}
