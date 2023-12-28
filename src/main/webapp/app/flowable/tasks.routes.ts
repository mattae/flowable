import { Routes } from '@angular/router';
import { TasksComponent } from './tasks/tasks.component';
import { TaskComponent } from './task/task.component';
import { taskResolve } from './work.routes';
import { CreateTaskComponent } from './tasks/create-task.component';

export default [
    {
        path: '',
        children: [
            {
                path: '',
                children: [
                    {
                        path: 'for-me',
                        component: TasksComponent,
                        data: {
                            filter: 'for-me'
                        }
                    },
                    {
                        path: 'unassigned',
                        component: TasksComponent,
                        data: {
                            filter: 'unassigned'
                        }
                    },
                    {
                        path: 'open',
                        component: TasksComponent,
                        data: {
                            filter: 'open'
                        }
                    },
                    {
                        path: 'completed',
                        component: TasksComponent,
                        data: {
                            filter: 'completed'
                        }
                    },
                    {
                        path: 'all',
                        component: TasksComponent,
                        data: {
                            filter: 'all'
                        }
                    },
                    {
                        path: 'new',
                        component: CreateTaskComponent,
                    },
                    {
                        path: ':taskId',
                        component: TasksComponent,
                        data: {
                            filter: 'all'
                        },
                        children: [
                            {
                                path: '',
                                component: TaskComponent,
                                resolve: {
                                    instance: taskResolve
                                }
                            }
                        ]
                    },
                ]
            }
        ]
    }
] as Routes;
