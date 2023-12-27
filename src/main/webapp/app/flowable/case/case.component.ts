import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTabsModule } from '@angular/material/tabs';
import { TranslocoModule } from '@ngneat/transloco';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Subject } from 'rxjs';
import { CaseInstance, PlanItemRepresentation } from '../model/case.model';
import { NgForOf, NgIf } from '@angular/common';
import { FormModel } from '../model/common.model';
import { CaseService } from '../services/case.service';
import { CovalentCommonModule } from '@covalent/core/common';
import { PeopleComponent } from '../people/people.component';
import { SubItemsComponent } from '../sub-items/sub-items.component';
import { DocumentsComponent } from '../documents/documents.component';
import { TaskService } from '../services/task.service';
import { FormService } from '../services/form.service';
import { MatFormioComponent } from '@mattae/angular-shared';

@Component({
    selector: 'flw-case',
    templateUrl: './case.component.html',
    standalone: true,
    changeDetection: ChangeDetectionStrategy.OnPush,
    imports: [
        MatButtonModule,
        MatIconModule,
        MatStepperModule,
        MatTabsModule,
        TranslocoModule,
        RouterLink,
        NgForOf,
        NgIf,
        CovalentCommonModule,
        PeopleComponent,
        SubItemsComponent,
        DocumentsComponent,
        MatFormioComponent
    ]
})
export class CaseComponent implements OnInit {
    instance: CaseInstance;
    private _unsubscribeAll: Subject<any> = new Subject<any>();
    enabledPlanItems: PlanItemRepresentation[] = [];
    form: FormModel;
    submission: any;

    constructor(private _router: Router,
                private _activatedRoute: ActivatedRoute,
                private _changeDetectorRef: ChangeDetectorRef,
                private _caseService: CaseService,
                private _formService: FormService,
                private _taskService: TaskService) {
    }

    ngOnInit(): void {
        this._activatedRoute.data.subscribe(({instance}) => {
            this.instance = instance;
            if (this.instance.startFormDefined) {
                this._caseService.getCaseInstanceStartForm(this.instance.id).subscribe(form => {
                    this.form = form;

                    this._formService.getFormData({
                        formDefinitionKey: form.key,
                        caseInstanceId: instance.id
                    }).subscribe(data => {
                        this.submission = {
                            data: data
                        };
                        this._changeDetectorRef.markForCheck();
                    });
                });
            }
            this.listEnabledTasks();

            this._changeDetectorRef.markForCheck();
        });

    }

    ngOnDestroy(): void {
        // Unsubscribe from all subscriptions
        this._unsubscribeAll.next(null);
        this._unsubscribeAll.complete();
    }

    startPlanItem(item: PlanItemRepresentation) {
        this._caseService.startPlanItem(this.instance.id, item.id).subscribe(res => {
            this.listEnabledTasks();
        });
    }

    listEnabledTasks(): void {
        this._caseService.enabledPlanItems(this.instance.id).subscribe(res => {
            this.enabledPlanItems = res.data.filter(pi => pi.planItemDefinitionType.includes('human-task'));
            this._changeDetectorRef.markForCheck();
        });
    }
}
