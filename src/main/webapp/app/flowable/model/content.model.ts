import { DateTime } from 'luxon';

export interface Content {
    id: string;
    name: string;
    contentAvailable: boolean;
    contentStoreId: string;
    contentStoreName: string;
    mimeType: string;
    simpleType: string;
    created: DateTime;
    createdBy: string;
}
