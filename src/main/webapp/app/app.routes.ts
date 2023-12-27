import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LayoutComponent } from './layout/layout.component';

export const APP_ROUTES: Routes = [
    {
        path: '',
        component: LayoutComponent,
        data: {
            layout: 'classy'
        },
        children: [
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
        ]
    }
];
