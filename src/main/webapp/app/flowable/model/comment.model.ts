import { DateTime } from 'luxon';
import { User } from './common.model';

export interface Comment {
    id?: string;
    message: string;
    created?: DateTime;
    createdBy?: string;
    user?: User;
}
