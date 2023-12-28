import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';

export const APP_ROUTES: Routes = [
    {
        path: '',
        component: HomeComponent,
        pathMatch: 'full'
    },
    {
        path: 'work',
        loadChildren: () => import('./flowable/work.routes')
    },
    {
        path: 'tasks',
        loadChildren: () => import('./flowable/tasks.routes')
    }
];
