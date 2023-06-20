import { Component } from '@angular/core';
import { MaterialNestedComponent } from '../MaterialNestedComponent';
import ColumnsComponent from 'formiojs/components/columns/Columns.js';

@Component({
    selector: 'mat-formio-columns',
    template: `
        <div class="grid grid-cols-1 gap-2"
             [ngClass]="getGrids()"
        >
            <div
                *ngFor="let column of instance.component.columns; let i = index">
                <ng-template #components></ng-template>
            </div>
        </div>
    `,
})
export class MaterialColumnsComponent extends MaterialNestedComponent {
    public flexGap = 0.5;
    public totalSpace = 0;
    public grids = 0;

    classes = {
        '1': 'sm:grid-cols-1',
        '2': 'sm:grid-cols-2',
        '3': 'sm:grid-cols-3',
        '4': 'sm:grid-cols-4',
        '5': 'sm:grid-cols-5',
        '6': 'sm:grid-cols-6',
        '7': 'sm:grid-cols-7',
        '8': 'sm:grid-cols-8',
        '9': 'sm:grid-cols-9',
        '10': 'sm:grid-cols-10',
        '11': 'sm:grid-cols-11',
        '12': 'sm:grid-cols-12',
    }

    setInstance(instance: any) {
        this.grids = instance.component.columns.length;
        this.totalSpace = 100 - ((instance.component.columns.length - 1) * this.flexGap);
        super.setInstance(instance);
        instance.viewContainer = (component) => {
            return this.viewContainers ? this.viewContainers[component.column] : null;
        };
    }

    getGrids() {
        return this.classes[`${this.grids}`];
    }
}

ColumnsComponent.MaterialComponent = MaterialColumnsComponent;
export { ColumnsComponent };
