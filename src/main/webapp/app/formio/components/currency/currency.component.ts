import { Component } from '@angular/core';
import { TEXTFIELD_TEMPLATE } from '../textfield/textfield.component';
import { MaterialNumberComponent } from '../number/number.component';
import CurrencyComponent from 'formiojs/components/currency/Currency.js';
import _ from 'lodash';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgIf } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { FormioFormFieldComponent } from '../formio-form-field/formio-form-field.component';
import { LabelComponent } from '../label/label.component';
import { TranslocoModule } from '@ngneat/transloco';

@Component({
    selector: 'mat-formio-currency',
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
export class MaterialCurrencyComponent extends MaterialNumberComponent {
    public inputType = 'text';

    onChange() {
        const newValue = _.isNil(this.getValue()) ? '' : this.getValue();
        this.instance.updateValue(newValue, {modified: true});
    }
}

CurrencyComponent.MaterialComponent = MaterialCurrencyComponent;
export { CurrencyComponent };
