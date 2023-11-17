import { Component } from '@angular/core';
import { MaterialTextfieldComponent, TEXTFIELD_TEMPLATE } from '../textfield/textfield.component';
import EmailComponent from 'formiojs/components/email/Email.js';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgIf } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { FormioFormFieldComponent } from '../formio-form-field/formio-form-field.component';
import { LabelComponent } from '../label/label.component';
import { TranslocoModule } from '@ngneat/transloco';

@Component({
    selector: 'mat-formio-email',
    template: TEXTFIELD_TEMPLATE,
    standalone: true,
    imports: [
        MatFormFieldModule,
        NgIf,
        ReactiveFormsModule,
        MatInputModule,
        FormioFormFieldComponent,
        LabelComponent,
        TranslocoModule
    ],
})
export class MaterialEmailComponent extends MaterialTextfieldComponent {
    public inputType = 'email';
}

EmailComponent.MaterialComponent = MaterialEmailComponent;
export { EmailComponent };
