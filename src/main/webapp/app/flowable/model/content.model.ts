import { DateTime } from 'luxon';

export interface Content {
    id: string;
    name: string;
    contentAvailable?: boolean;
    contentStoreId?: string;
    contentStoreName?: string;
    mimeType: string;
    simpleType?: string;
    created: DateTime;
    lastModified: DateTime;
    contentSize: number;
    createdBy?: string;
    definitionId?: string;
    definitionName?: string
    type?: string;
    renditionAvailable?: boolean;
}

export interface DocumentDefinition {
    id: string;
    key: string;
    name: string;
    category: string;
}
