import { Component } from '@angular/core';
import FieldsetComponent from 'formiojs/components/fieldset/Fieldset.js';
import { MaterialNestedComponent } from '../MaterialNestedComponent';
import { MatIconModule } from '@angular/material/icon';
import { NgIf } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTooltipModule } from '@angular/material/tooltip';
import { TranslocoModule } from '@ngneat/transloco';

@Component({
    selector: 'mat-formio-fieldset',
    template: `
        <fieldset>
            <legend [attr.ref]="'header'">
                {{ instance.component.legend }}
                <mat-icon *ngIf="instance.component.tooltip"
                          matSuffix
                          matTooltip="{{ instance.component.tooltip | transloco}}"
                >
                    info
                </mat-icon>
            </legend>
            <div class="fieldset-body flex flex-col" [attr.ref]="instance.component.key">
                <ng-template #components></ng-template>
            </div>
        </fieldset>
    `,
    styles: [],
    imports: [
        MatIconModule,
        NgIf,
        MatFormFieldModule,
        MatTooltipModule,
        TranslocoModule,
    ],
    standalone: true
})
export class MaterialFieldsetComponent extends MaterialNestedComponent {
}

FieldsetComponent.MaterialComponent = MaterialFieldsetComponent;
export { FieldsetComponent };
