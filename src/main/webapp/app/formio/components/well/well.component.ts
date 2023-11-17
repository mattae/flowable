import { Component } from '@angular/core';
import WellComponent from 'formiojs/components/well/Well.js';
import { MaterialNestedComponent } from '../MaterialNestedComponent';
import { MatCardModule } from '@angular/material/card';
import { NgStyle } from '@angular/common';

@Component({
    selector: 'mat-formio-well',
    template: `
        <mat-card>
            <mat-card-content class="flex flex-col gap-1"
                              [ngStyle]="{
                           'background-color':'#f5f5f5',
                           'padding': '1.5em'
                         }"
            >
                <ng-template #components></ng-template>
            </mat-card-content>
        </mat-card>
    `,
    styles: [],
    imports: [
        MatCardModule,
        NgStyle
    ],
    standalone: true
})
export class MaterialWellComponent extends MaterialNestedComponent {}
WellComponent.MaterialComponent = MaterialWellComponent;
export { WellComponent };
