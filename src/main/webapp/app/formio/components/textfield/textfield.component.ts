import { AfterContentInit, Component } from '@angular/core';
import TextFieldComponent from 'formiojs/components/textfield/TextField.js';
import { MaterialComponent } from '../MaterialComponent';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgIf } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { FormioFormFieldComponent } from '../formio-form-field/formio-form-field.component';
import { LabelComponent } from '../label/label.component';
import { TranslocoModule } from '@ngneat/transloco';

export const TEXTFIELD_TEMPLATE = `
  <mat-formio-form-field [instance]="instance" [componentTemplate]="componentTemplate"></mat-formio-form-field>
  <ng-template #componentTemplate let-hasLabel>
    <mat-form-field [appearance]="getFormFieldAppearance()" class="w-full">

      <mat-label *ngIf="hasLabel">
          <span [instance]="instance" matFormioLabel></span>
      </mat-label>

      <span *ngIf="instance.component.prefix && instance.type !== 'currency'"
            matPrefix
      >
        {{ instance.component.prefix }}&nbsp;
      </span>
      <input matInput
            type="{{ inputType }}"
            [formControl]="control"
            [placeholder]="instance.component.placeholder | transloco"
            (input)="onChange()" #input
      >
      <span *ngIf="instance.component.suffix" matSuffix>{{ instance.component.suffix }}</span>

      <mat-hint *ngIf="instance.component.showWordCount || instance.component.showCharCount">
        {{ getHint() }}
      </mat-hint>

      <br/>
      <mat-error *ngIf="isError()" >{{ getErrorMessage()  | transloco}}</mat-error>
    </mat-form-field>
  </ng-template>
`;

@Component({
    selector: 'mat-formio-textfield',
    template: TEXTFIELD_TEMPLATE,
    imports: [
        MatFormFieldModule,
        NgIf,
        ReactiveFormsModule,
        MatInputModule,
        FormioFormFieldComponent,
        LabelComponent,
        TranslocoModule
    ],
    standalone: true
})
export class MaterialTextfieldComponent extends MaterialComponent implements AfterContentInit {
    public inputType = 'text';

    ngAfterContentInit() {
        if (this.instance && this.control && this.instance.component.disabled) {
            this.control.disable();
        }
    }

    getHint() {
        if (!this.instance || !this.control || !this.control.value) {
            return '';
        }

        const {showWordCount, showCharCount} = this.instance.component;

        if (showWordCount && showCharCount) {
            return `${this.getWordsCount()} words, ${this.control.value.length} characters`;
        } else if (showWordCount) {
            return `${this.getWordsCount()} words`;
        } else {
            return `${this.control.value.length} characters`;
        }
    }

    getWordsCount() {
        const matches = this.control.value ? this.control.value.match(/[\w\d’'-]+/gi) : [];
        return matches ? matches.length : 0;
    }

    getFormFieldAppearance() {
        const appearances = ['legacy', 'standard', 'fill', 'outline'];
        const appearance = this.instance.component.appearance ? this.instance.component.appearance.toLowerCase() : '';
        return appearances.includes(appearance) ? appearance : undefined;
    }

    isError() {
        if (this.instance.error) {
            this.control.setErrors(this.instance.component.validate);
            return true;
        } else {
            return false;
        }
    }

    getErrorMessage() {
        if (this.instance.error && this.instance.error.messages) {
            const {messages} = this.instance.error;

            for (const msg of messages) {
                if (msg.context && (this.control.hasError(msg.context.validator) || msg.context.validator)) {
                    return this.instance.error.message;
                }
            }
        }
    }
}

TextFieldComponent.MaterialComponent = MaterialTextfieldComponent;
export { TextFieldComponent };
