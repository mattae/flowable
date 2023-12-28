import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { MaterialComponent } from '../MaterialComponent';
import TextAreaComponent from 'formiojs/components/textarea/TextArea.js';
import isNil from 'lodash/isNil';
import { FormioFormFieldComponent } from '../formio-form-field/formio-form-field.component';
import { MatInputModule } from '@angular/material/input';
import { NgClass, NgIf } from '@angular/common';
import { LabelComponent } from '../label/label.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TranslocoModule } from '@ngneat/transloco';

@Component({
    selector: 'mat-formio-textarea',
    styleUrls: ['./textarea.component.css'],
    template: `
        <mat-formio-form-field [instance]="instance" [componentTemplate]="componentTemplate"></mat-formio-form-field>
        <ng-template #componentTemplate let-hasLabel>
            <mat-form-field class="mat-formio-textarea w-full h-full self-center"
                            [ngClass]="{'editor-enabled': !!instance.component.editor}"
            >
                <mat-label class="w-full" *ngIf="hasLabel">
                    <span [instance]="instance" matFormioLabel></span>
                </mat-label>
                <span *ngIf="instance.component.prefix" matPrefix>{{ instance.component.prefix }}&nbsp;</span>
                <textarea matInput
                          class="w-full"
                          [placeholder]="instance.component.placeholder"
                          [formControl]="control"
                          [rows]="(instance.component.rows || 3)"
                          (input)="onChange()"
                          #textarea
                >
                </textarea>
                <span *ngIf="instance.component.suffix" matSuffix>{{ instance.component.suffix }}</span>
                <mat-error *ngIf="instance.error">{{ instance.error.message | transloco }}</mat-error>
            </mat-form-field>
        </ng-template>
    `,
    imports: [
        FormioFormFieldComponent,
        MatInputModule,
        NgIf,
        LabelComponent,
        NgClass,
        ReactiveFormsModule,
        TranslocoModule
    ],
    standalone: true
})
export class MaterialTextareaComponent extends MaterialComponent implements AfterViewInit {
  @ViewChild('textarea') textarea!: ElementRef;

  ngAfterViewInit() {
    // Attach the element so the wysiwyg will work.
    this.instance.attachElement(this.textarea.nativeElement);
  }

  getValue() {
    return isNil(this.control.value) ? '' : this.control.value;
  }
}

TextAreaComponent.MaterialComponent = MaterialTextareaComponent;
export { TextAreaComponent };