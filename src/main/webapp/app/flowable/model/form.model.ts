export interface FormDeployment {
    id: string;
    name: string;
    tenantId: string;
}

export interface FormDefinition {
    id: string;
    name: string;
    key: string;
    description: string;
    deploymentId: string;
    tenantId: string;
}
