import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { TranslocoModule } from '@ngneat/transloco';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { ProcessInstance } from '../model/process.model';
import { ProcessService } from '../services/process.service';
import { FormModel } from '../model/common.model';
import { NgIf } from '@angular/common';
import { CovalentCommonModule } from '@covalent/core/common';
import { DocumentsComponent } from '../documents/documents.component';
import { PeopleComponent } from '../people/people.component';
import { SubItemsComponent } from '../sub-items/sub-items.component';
import { CommentsComponent } from '../comments/comments.component';
import { FormService } from '../services/form.service';
import { TaskService } from '../services/task.service';
import { MatFormioComponent } from '@mattae/angular-shared';

@Component({
    selector: 'flowable-process',
    templateUrl: './process.component.html',
    standalone: true,
    imports: [
        MatIconModule,
        TranslocoModule,
        RouterLink,
        MatButtonModule,
        MatTabsModule,
        NgIf,
        CovalentCommonModule,
        DocumentsComponent,
        PeopleComponent,
        SubItemsComponent,
        CommentsComponent,
        MatFormioComponent
    ],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProcessComponent implements OnInit {
    instance: ProcessInstance;
    form: FormModel;
    submission: any;
    constructor(private _router: Router,
                private _activatedRoute: ActivatedRoute,
                private _processService: ProcessService,
                private _formService: FormService,
                private _taskService: TaskService,
                private _changeDetectorRef: ChangeDetectorRef) {
    }

    ngOnInit(): void {
        this._activatedRoute.data.subscribe(({instance}) => {
            this.instance = instance;
            if (this.instance.startFormDefined) {
                this._processService.getProcessInstanceStartForm(this.instance.id).subscribe(form => {
                    this.form = form;
                    this._formService.getFormData({
                        formDefinitionKey: form.key,
                        processInstanceId: instance.id
                    }).subscribe(data => {
                        this.submission = {
                            data: data
                        };
                        this._changeDetectorRef.markForCheck();
                    });
                });
            }
        });

        this._changeDetectorRef.markForCheck();
    }
}
