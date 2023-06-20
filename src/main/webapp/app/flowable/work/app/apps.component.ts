import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
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
import { DateTime } from 'luxon';
import { NgxPrettyDateModule } from 'ngx-pretty-date';
import { CovalentCommonModule } from '@covalent/core/common';
import { MatDialog } from '@angular/material/dialog';
import { CreateDialogComponent } from './dialog/dialog.component';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'apps',
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
        NgxPrettyDateModule,
        CovalentCommonModule,
    ],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppsComponent implements OnInit {
    workForms: WorkForm[];
    filteredWorkForms: WorkForm[];
    time = DateTime.now().minus({seconds: 30})

    constructor(private _workService: WorkService,
                private _cdr: ChangeDetectorRef,
                private _processService: ProcessService,
                private _caseService: CaseService,
                private _dialog: MatDialog,
                private _router: Router,
                private _activatedRoute: ActivatedRoute) {
    }

    definitionSelected(event: DefinitionEvent) {
        let hasStartForm = event.definition.hasStartFormKey;
        if (hasStartForm) {
            if (event.definition.type === 'process') {
                this._processService.getProcessDefinitionStartForm(event.definition.id).subscribe(res => {
                    const startForm = res;
                    startForm.components = startForm.fields.filter(c => c['key'] != 'submit');
                    const ref = this._dialog.open(CreateDialogComponent, {
                        data: {
                            form: startForm,
                            title: 'FLOWABLE.APP.CREATE.PROCESS'
                        }
                    });
                    ref.afterClosed().subscribe(data => {
                        if (data) {
                            this._processService.createProcessInstance({
                                processDefinitionId: event.definition.id,
                                values: data,
                                formId: ''
                            }).subscribe(res => {
                                this._router.navigate(['process', res.id], {relativeTo: this._activatedRoute})
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
                            title: 'FLOWABLE.APP.CREATE.CASE'
                        }
                    });
                    ref.afterClosed().subscribe(data => {
                        if (data) {
                            this._caseService.createCaseInstance({
                                caseDefinitionId: event.definition.id,
                                values: data,
                                formId: ''
                            }).subscribe(res => {
                                this._router.navigate(['case', res.id], {relativeTo: this._activatedRoute})
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
                    this._router.navigate(['processes', res.id], {relativeTo: this._activatedRoute})
                });
            } else {
                this._caseService.createCaseInstance({
                    caseDefinitionId: event.definition.id,
                    formId: ''
                }).subscribe(res => {
                    this._router.navigate(['cases', res.id], {relativeTo: this._activatedRoute})
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
