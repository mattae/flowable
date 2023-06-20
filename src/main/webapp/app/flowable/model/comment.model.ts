import { DateTime } from 'luxon';

export interface Comment {
    id: string;
    message: string;
    created: DateTime;
    createdBy: string;
}
