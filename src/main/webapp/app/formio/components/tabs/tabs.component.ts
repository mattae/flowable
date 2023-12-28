import { Component } from '@angular/core';
import { MaterialNestedComponent } from '../MaterialNestedComponent';
import TabsComponent from 'formiojs/components/tabs/Tabs.js';
import { MatTabsModule } from '@angular/material/tabs';
import { TranslocoModule } from '@ngneat/transloco';
import { NgForOf } from '@angular/common';

Object.defineProperty(TabsComponent.prototype, 'visible', {
    set(visible) {
        if (this._visible !== visible) {
            this._visible = visible;
            this.clearOnHide();
            this.redraw();
        }
        if (this.materialComponent) {
            this.materialComponent.setVisible(visible);
            this.redraw();
        }
    }
});

@Component({
    selector: 'mat-formio-tabs',
    imports: [
        MatTabsModule,
        TranslocoModule,
        NgForOf
    ],
    template: `
        <mat-tab-group>
            <mat-tab *ngFor="let tab of instance.component.components" [label]="tab.label | transloco">
                <div class="flex flex-col gap-1" style="border: 1px dotted rgba(0, 0, 0, 0.125)">
                    <ng-template #components></ng-template>
                </div>
            </mat-tab>
        </mat-tab-group>
    `,
    standalone: true
})
export class MaterialTabsComponent extends MaterialNestedComponent {
    setInstance(instance: any) {
        super.setInstance(instance);
        instance.viewContainer = (component) => {
            return this.viewContainers ? this.viewContainers[component.tab] : null;
        };
    }
}

TabsComponent.MaterialComponent = MaterialTabsComponent;
export { TabsComponent };
