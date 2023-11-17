import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { User } from '../model/common.model';
import { NgForOf, NgIf } from '@angular/common';
import { TranslocoModule, TranslocoService } from '@ngneat/transloco';
import { Content } from '../model/content.model';
import { DateTime } from 'luxon';
import { CovalentCommonModule } from '@covalent/core/common';
import { ContentService } from '../services/content.service';
import { catchError, EMPTY, map } from 'rxjs';
import { LuxonModule } from 'luxon-angular';
import { MatIconModule } from '@angular/material/icon';
import { FuseAlertComponent, FuseScrollbarDirective } from '@mattae/angular-shared';

@Component({
    selector: 'flw-documents',
    templateUrl: './documents.component.html',
    standalone: true,
    changeDetection: ChangeDetectionStrategy.OnPush,
    imports: [
        MatButtonModule,
        NgIf,
        TranslocoModule,
        NgForOf,
        CovalentCommonModule,
        FuseAlertComponent,
        LuxonModule,
        MatIconModule,
        FuseScrollbarDirective
    ]
})
export class DocumentsComponent implements OnInit {
    @Input()
    processInstanceId: string;
    @Input()
    taskId: string;
    @Input()
    caseInstanceId: string;
    @Input()
    assignee: User;
    @Input()
    readonly = false;
    documents: Content[];
    @ViewChild('upload') fileInput: any;
    showAlert = false;
    langKey = 'en';

    constructor(private _contentService: ContentService, private _translocoService: TranslocoService,
                private _changeDetectorRef: ChangeDetectorRef) {
    }

    ngOnInit(): void {
        this._translocoService.langChanges$.subscribe(lang => {
            this.langKey = lang;
        });

        this.getContents();

        this._changeDetectorRef.markForCheck();
    }

    click(input: HTMLInputElement) {
        input.value = ''
        this.showAlert = false;
        this.fileInput.nativeElement.click();

        this._changeDetectorRef.markForCheck();
    }

    getContents() {
        if (this.taskId) {
            this._contentService.getContentItemsForTask(this.taskId).subscribe(res => {
                this.documents = res.data;

                this._changeDetectorRef.markForCheck();
            });
        } else if (this.processInstanceId) {
            this._contentService.getContentItemsForProcessInstance(this.processInstanceId).subscribe(res => {
                this.documents = res.data;

                this._changeDetectorRef.markForCheck();
            });
        } else if (this.caseInstanceId) {
            this._contentService.getContentItemsForCaseInstance(this.caseInstanceId).subscribe(res => {
                this.documents = res.data;

                this._changeDetectorRef.markForCheck();
            });
        }
    }

    addContent(event) {
        const file = event.target.files[0];
        if (file) {
            const formData = new FormData();
            formData.append('file', file);
            if (this.taskId) {
                this._contentService.createRawContentItemOnTask(this.taskId, formData).pipe(
                    map(res => {
                        this.getContents();
                    }),
                    catchError(err => {
                        this.showAlert = true;
                        return EMPTY;
                    })
                ).subscribe();
            } else if (this.processInstanceId) {
                this._contentService.createRawContentItemOnProcessInstance(this.processInstanceId, formData).subscribe(res => {
                    this.getContents();
                });
            } else if (this.caseInstanceId) {
                this._contentService.createRawContentItemOnCaseInstance(this.caseInstanceId, formData).subscribe(res => {
                    this.getContents();
                });
            }
        }
    }

}
