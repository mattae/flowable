import { ChangeDetectionStrategy, ChangeDetectorRef, Component, forwardRef, OnInit } from '@angular/core';
import { NgForOf, NgIf } from '@angular/common';
import { DragUploadDirective, FileHandle } from './upload-directive';
import { TranslocoModule } from '@ngneat/transloco';
import { CovalentCommonModule } from '@covalent/core/common';
import { MatIconModule } from '@angular/material/icon';
import { MatFormioComponent } from '../../mat-formio.component';
import { FormioFormFieldComponent } from '../formio-form-field/formio-form-field.component';
import { LabelComponent } from '../label/label.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ContentService } from '../../../flowable/services/content.service';
import { Upload } from 'ngx-operators';
import { Content, DocumentDefinition } from '../../../flowable/model/content.model';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { MtxPopoverModule } from '@ng-matero/extensions/popover';

@Component({
    selector: 'file-upload',
    templateUrl: './upload.component.html',
    standalone: true,
    changeDetection: ChangeDetectionStrategy.OnPush,
    imports: [
        NgIf,
        DragUploadDirective,
        TranslocoModule,
        NgForOf,
        CovalentCommonModule,
        MatIconModule,
        MatFormioComponent,
        forwardRef(() => UploadComponent),
        FormioFormFieldComponent,
        LabelComponent,
        MatFormFieldModule,
        MatProgressBarModule,
        MatSelectModule,
        MtxPopoverModule
    ]
})
export class UploadComponent implements OnInit {
    visible: boolean = true;
    instance: any;
    disabled: boolean = false;
    value: any;
    files: File[];
    documentTypes: DocumentDefinition[];
    uploads: { file: File, upload: Upload<Content> }[] = [];
    uploads1: Upload<Content>[];
    form: any;

    constructor(private _contentService: ContentService,
                private _changeDetectorRef: ChangeDetectorRef) {
    }

    ngOnInit(): void {
    }

    setInstance(instance: any) {
        this.instance = instance;
        if (this.instance.component.definitionId) {

        } else if (this.instance.component.definitionName) {

        }
    }

    setDisabled(disabled) {
        this.disabled = disabled;
    }

    setVisible(visible) {
        this.visible = visible;
    }

    setValue(value) {
        if (value) {
            this.value = value;
            this.loadRendition();
        }
    }

    loadFiles(files: FileHandle[]) {

    }

    loadRendition() {

    }

    click(input: HTMLInputElement) {
        input.value = '';

        this._changeDetectorRef.markForCheck();
    }

    remove(index: any) {

    }

    loadFile(file) {
        const preview = document.querySelectorAll('.preview');
        const blobUrl = URL.createObjectURL(file);

        preview.forEach(elem => {
            elem.onload = () => {
                URL.revokeObjectURL(elem.src); // free memory
            };
        });

        return blobUrl;
    }

    onChange(event) {
        this.files = [];
        this.files.push(event.target.files[0]);

        this._changeDetectorRef.markForCheck();
    }
}
