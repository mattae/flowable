import { WorkComponent } from './work/work.component';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, Routes } from '@angular/router';
import { TaskComponent } from './task/task.component';
import { CaseComponent } from './case/case.component';
import { ProcessComponent } from './process/process.component';
import { catchError, EMPTY, Observable } from 'rxjs';
import { ProcessInstance } from './model/process.model';
import { inject } from '@angular/core';
import { ProcessService } from './services/process.service';
import { CaseService } from './services/case.service';
import { CaseInstance } from './model/case.model';
import { TaskInstance } from './model/task.model';
import { TasksService } from './services/tasks.service';

const processResolve = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ProcessInstance> => {
    const id = route.params['processId'];
    const router = inject(Router);
    return inject(ProcessService).getProcessInstance(id).pipe(
        catchError((err) => {
            router.navigateByUrl('/work');
            return EMPTY;
        })
    );
}

const caseResolve = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CaseInstance> => {
    const id = route.params['caseId'];
    const router = inject(Router);
    return inject(CaseService).getCaseInstance(id).pipe(
        catchError((err) => {
            router.navigateByUrl('/work');
            return EMPTY;
        })
    );
}

const taskResolve = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TaskInstance> => {
    const id = route.params['taskId'];
    const router = inject(Router);
    return inject(TasksService).getTask(id).pipe(
        catchError((err) => {
            router.navigateByUrl('/work');
            return EMPTY;
        })
    );
}
export default [
    {
        path: '',
        children: [
            {
                path: '',
                component: WorkComponent,
                children: [
                    {
                        path: 'cases/:caseId',
                        component: CaseComponent,
                        resolve: {
                            instance: caseResolve
                        }
                    },
                    {
                        path: 'processes/:processId',
                        component: ProcessComponent,
                        resolve: {
                            instance: processResolve
                        }
                    },
                    {
                        path: 'tasks/:taskId',
                        component: TaskComponent,
                        resolve: {
                            instance: taskResolve
                        }
                    }
                ]
            }
        ]
    }
] as Routes;
