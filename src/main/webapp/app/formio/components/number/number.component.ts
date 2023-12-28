import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, Renderer2 } from '@angular/core';
import { MaterialTextfieldComponent, TEXTFIELD_TEMPLATE } from '../textfield/textfield.component';
import NumberComponent from 'formiojs/components/number/Number.js';
import _ from 'lodash';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgIf } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { FormioFormFieldComponent } from '../formio-form-field/formio-form-field.component';
import { LabelComponent } from '../label/label.component';
import { TranslocoModule } from '@ngneat/transloco';

@Component({
    selector: 'mat-formio-number',
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
export class MaterialNumberComponent extends MaterialTextfieldComponent implements AfterViewInit {
    public inputType = 'text';

    constructor(public element: ElementRef, public ref: ChangeDetectorRef, private renderer: Renderer2) {
        super(element, ref);
    }

    ngAfterViewInit() {
        super.ngAfterViewInit();
        if (this.instance) {
            const {instance} = this;

            this.renderer.listen(this.input.nativeElement, 'blur', () => {
                let value = instance.parseValue(this.control.value);
                value = instance.formatValue(value);
                value = instance.getValueAsString(value);
                this.control.setValue(value);
            });

        }
    }

    getValue() {
        let value = this.control.value;
        if (value && this.instance) {
            value = value.replace(this.instance.prefix, '');
            return !_.isNil(value) && value !== '' ? this.instance.parseNumber(value) : value;
        }
        return value;
    }

    setValue(value) {
        if (this.instance) {
            const {instance} = this;
            value = instance.formatValue(instance.parseValue(value));
        } else {
            value = value.toString();
        }

        return super.setValue(value);
    }

    onChange() {
        super.onChange(true);
    }
}

NumberComponent.MaterialComponent = MaterialNumberComponent;
export { NumberComponent };
