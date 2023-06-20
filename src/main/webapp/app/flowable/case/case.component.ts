import { ChangeDetectionStrategy, Component, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatFormioModule } from '../../formio/angular-material-formio.module';
import { MatIconModule } from '@angular/material/icon';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTabsModule } from '@angular/material/tabs';
import { TranslocoModule } from '@ngneat/transloco';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatDrawer } from '@angular/material/sidenav';
import { Subject } from 'rxjs';
import { CaseInstance } from '../model/case.model';
import { TaskInstance } from '../model/task.model';
import { NgForOf, NgIf } from '@angular/common';
import { ProcessInstance } from '../model/process.model';
import { FormModel } from '../model/common.model';
import { CaseService } from '../services/case.service';

@Component({
    selector: 'flowable-case',
    templateUrl: './case.component.html',
    standalone: true,
    changeDetection: ChangeDetectionStrategy.OnPush,
    imports: [
        MatButtonModule,
        MatFormioModule,
        MatIconModule,
        MatStepperModule,
        MatTabsModule,
        TranslocoModule,
        RouterLink,
        NgForOf,
        NgIf

    ]
})
export class CaseComponent implements OnInit {
    caseInstance: CaseInstance;
    @ViewChild('matDrawer', {static: true}) matDrawer: MatDrawer;
    drawerMode: 'side';
    private _unsubscribeAll: Subject<any> = new Subject<any>();
    openTasks: TaskInstance[] = [];
    instance: ProcessInstance;
    form: FormModel;

    constructor(private _router: Router, private _activatedRoute: ActivatedRoute, private _caseService: CaseService) {
    }

    ngOnInit(): void {
        this._activatedRoute.data.subscribe(({instance}) => {
            this.instance = instance;
            if (this.instance.startFormDefined) {
                this._caseService.getCaseInstanceStartForm(this.instance.id).subscribe(form => {
                    this.form = form;
                });
            }
        });
    }

    ngOnDestroy(): void {
        // Unsubscribe from all subscriptions
        this._unsubscribeAll.next(null);
        this._unsubscribeAll.complete();
    }

    taskSelected(task: TaskInstance): void {
        this._router.navigate(['../tasks', task.id], {relativeTo: this._activatedRoute})
    }

    onSubmit(event) {
    }
}
