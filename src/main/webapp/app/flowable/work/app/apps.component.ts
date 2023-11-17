import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, ViewEncapsulation } from '@angular/core';
import { WorkService } from '../../services/work.service';
import { WorkForm } from '../../model/work.model';
import { NgForOf, NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { TranslocoModule } from '@ngneat/transloco';
import { AppDefComponent, DefinitionEvent } from './app.component';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { ProcessService } from '../../services/process.service';
import { CaseService } from '../../services/case.service';
import { MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { CreateDialogComponent } from './dialog/dialog.component';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'flw-apps',
    templateUrl: './apps.component.html',
    standalone: true,
    imports: [
        NgForOf,
        MatIconModule,
        TranslocoModule,
        AppDefComponent,
        MatInputModule,
        NgIf,
        MatButtonModule,
        MatDialogModule
    ],
    changeDetection: ChangeDetectionStrategy.OnPush,
    encapsulation: ViewEncapsulation.None,
    styles: [
        `
            .dialog-content {
                padding: unset;
            }

        `
    ]
})
export class AppsComponent implements OnInit {
    workForms: WorkForm[] = [];
    filteredWorkForms: WorkForm[] = [];

    constructor(private _workService: WorkService,
                private _cdr: ChangeDetectorRef,
                private _processService: ProcessService,
                private _caseService: CaseService,
                private _dialog: MatDialog,
                private _dialogRef: MatDialogRef<AppsComponent>,
                private _router: Router,
                private _activatedRoute: ActivatedRoute) {
    }

    definitionSelected(event: DefinitionEvent) {
        this._dialogRef.close();

        let hasStartForm = event.definition.hasStartForm;
        if (hasStartForm) {
            if (event.definition.type === 'process') {
                this._processService.getProcessDefinitionStartForm(event.definition.id).subscribe(res => {
                    const startForm = res;
                    startForm.components = startForm.fields.filter(c => c['key'] != 'submit');
                    const ref = this._dialog.open(CreateDialogComponent, {
                        data: {
                            form: startForm,
                            title: 'FLOWABLE.APPS.CREATE.PROCESS'
                        }
                    });
                    ref.afterClosed().subscribe(data => {
                        if (data) {
                            this._processService.createProcessInstance({
                                processDefinitionId: event.definition.id,
                                values: data,
                                formId: ''
                            }).subscribe(res => {
                                this._router.navigate(['/work/processes', res.id], {relativeTo: this._activatedRoute})
                            });
                        }
                    });
                })
            } else {
                this._caseService.getCaseDefinitionStartForm(event.definition.id).subscribe(res => {
                    const startForm = res;
                    startForm.components = startForm.fields.filter(c => c['key'] != 'submit');
                    const ref = this._dialog.open(CreateDialogComponent, {
                        data: {
                            form: startForm,
                            title: 'FLOWABLE.APPS.CREATE.CASE'
                        }
                    });
                    ref.afterClosed().subscribe(data => {
                        if (data) {
                            this._caseService.createCaseInstance({
                                caseDefinitionId: event.definition.id,
                                values: data,
                                formId: ''
                            }).subscribe(res => {
                                this._router.navigate(['/work/cases', res.id], {relativeTo: this._activatedRoute})
                            });
                        }
                    });
                })
            }
        } else {
            if (event.definition.type === 'process') {
                this._processService.createProcessInstance({
                    processDefinitionId: event.definition.id,
                    formId: ''
                }).subscribe(res => {
                    this._router.navigate(['/work/processes', res.id], {relativeTo: this._activatedRoute})
                });
            } else {
                this._caseService.createCaseInstance({
                    caseDefinitionId: event.definition.id,
                    formId: ''
                }).subscribe(res => {
                    this._router.navigate(['/work/cases', res.id], {relativeTo: this._activatedRoute})
                });
            }
        }
    }

    filterApps(keyword: string) {
        if (!keyword) {
            this.filteredWorkForms = [...this.workForms];
        } else {
            this.filteredWorkForms = this.workForms.map(wf => {
                const filteredDefinitions = wf.definitions.filter(d => d.name.toLowerCase().includes(keyword.toLowerCase()));
                return {...wf, definitions: filteredDefinitions};
            }).filter(wf => wf.definitions.length);
        }

        this._cdr.markForCheck();
    }

    ngOnInit(): void {
        this._workService.getWorkForm().subscribe(res => {
            this.workForms = res;
            this.filteredWorkForms = [...res];

            this._cdr.markForCheck();
        });
    }
}
