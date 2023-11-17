import { Component } from '@angular/core';
import { MaterialComponent } from '../MaterialComponent';
import CheckboxComponent from 'formiojs/components/checkbox/Checkbox.js';
import _ from 'lodash';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { NgClass, NgIf } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { LabelComponent } from '../label/label.component';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTooltipModule } from '@angular/material/tooltip';
import { TranslocoModule } from '@ngneat/transloco';

@Component({
    selector: 'mat-formio-checkbox',
    template: `
        <mat-checkbox (change)="onChange()" [ngClass]="{'validation-error' : !!instance.error}"
                      [formControl]="control"
        >
            <span matFormioLabel [instance]="instance"></span>
            <mat-icon *ngIf="instance.component.tooltip" matSuffix
                      matTooltip="{{ instance.component.tooltip | transloco}}" style="font-size: 1rem;">info
            </mat-icon>
        </mat-checkbox>
        <mat-hint>
            {{ instance.component.description | transloco }}
        </mat-hint>
        <mat-error *ngIf="instance.error">{{ instance.error.message | transloco}}</mat-error>
    `,
    styles: ['::ng-deep .mat-checkbox.validation-error .mat-checkbox-frame {border-color: red; }'],
    imports: [
        MatCheckboxModule,
        NgClass,
        ReactiveFormsModule,
        LabelComponent,
        MatIconModule,
        MatFormFieldModule,
        MatTooltipModule,
        NgIf,
        TranslocoModule
    ],
    standalone: true
})
export class MaterialCheckboxComponent extends MaterialComponent {
    getValue() {
        return _.isNil(this.control.value) ? '' : this.control.value;
    }
}

CheckboxComponent.MaterialComponent = MaterialCheckboxComponent;
export { CheckboxComponent };
