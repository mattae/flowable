import { Component } from '@angular/core';
import { MaterialTextfieldComponent, TEXTFIELD_TEMPLATE } from '../textfield/textfield.component';
import PasswordComponent from 'formiojs/components/password/Password.js';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgIf } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { FormioFormFieldComponent } from '../formio-form-field/formio-form-field.component';
import { LabelComponent } from '../label/label.component';
import { TranslocoModule } from '@ngneat/transloco';

@Component({
    selector: 'mat-formio-password',
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
export class MaterialPasswordComponent extends MaterialTextfieldComponent {
    public inputType = 'password';
}

PasswordComponent.MaterialComponent = MaterialPasswordComponent;
export { PasswordComponent };
