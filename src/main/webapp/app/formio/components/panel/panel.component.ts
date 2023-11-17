import { Component } from '@angular/core';
import { MaterialNestedComponent } from '../MaterialNestedComponent';
import PanelComponent from 'formiojs/components/panel/Panel.js';
import { MatExpansionModule } from '@angular/material/expansion';
import { NgIf } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { TranslocoModule } from '@ngneat/transloco';

Object.defineProperty(PanelComponent.prototype, 'visible', {
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
    selector: 'mat-formio-panel',
    template: `
        <mat-card *ngIf="!instance.component.collapsible">
            <mat-card-title *ngIf="instance?.component?.title">
                {{ instance.component.title | transloco}}
            </mat-card-title>
            <mat-card-content class="flex flex-col">
                <ng-template #components></ng-template>
            </mat-card-content>
        </mat-card>
        <mat-expansion-panel *ngIf="instance.component.collapsible"
                             [expanded]="!instance.component.collapsed"
        >
            <mat-expansion-panel-header *ngIf="instance?.component?.title">
                <mat-panel-title>
                    {{ instance.component.title | transloco}}
                </mat-panel-title>
            </mat-expansion-panel-header>
            <ng-template #components></ng-template>
        </mat-expansion-panel>
    `,
    styles: [
        ':host { margin-bottom: 1em; }'
    ],
    imports: [
        MatExpansionModule,
        NgIf,
        MatCardModule,
        TranslocoModule
    ],
    standalone: true
})
export class MaterialPanelComponent extends MaterialNestedComponent {
}

PanelComponent.MaterialComponent = MaterialPanelComponent;
export { PanelComponent };
