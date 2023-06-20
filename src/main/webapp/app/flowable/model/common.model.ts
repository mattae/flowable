export interface RestVariable {
    name: string;
    type: string;
    variableScope: 'LOCAL' | 'GLOBAL';
    value: any;
    valueUrl: string;
}

export interface Group {
    id: string;
    name: string;
    type: string;
}

export interface User {
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    fullName: string;
    tenantId: string;
    groups: Group[];
}

export interface FormOutcome {
    id: string;
    name: string;
}

export interface FormModel {
    id?: string;
    name?: string;
    description?: string;
    key: string;
    version: number;
    fields: { [key: string]: any; }[];
    components: { [key: string]: any; }[];
    outcomes?: FormOutcome[];
    outcomeVariableName?: String;
}

export interface SaveForm {
    formId: string;
    values?: { [key: string]: any; };
}

export interface CompleteForm extends SaveForm {
    outcome?: string;
}

export interface ListResult<T> {
    data: T[];
    start: number;
    total: number;
    size: number;
}
