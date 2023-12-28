import { Component, ViewChild } from '@angular/core';
import { MatStepper, MatStepperModule } from '@angular/material/stepper';
import Wizard from 'formiojs/Wizard';
import Displays from 'formiojs/displays/Displays';
import { MaterialNestedComponent } from './MaterialNestedComponent';
import { NgForOf, NgIf } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'mat-formio-wizard',
    styles: [
        ':host .navigation-button-row { margin-top: 8px; }',
        ':host .navigation-button-row button { margin-right: 8px; }'
    ],
    template: `
        <mat-horizontal-stepper [linear]="isLinear" #stepper>
            <mat-step *ngFor="let page of instance.components" [label]="page.component.title">
                <ng-template #components></ng-template>
                <div class="navigation-button-row">
                    <button *ngIf="stepper.selectedIndex > 0 " mat-raised-button color="primary"
                            (click)="prevPage()">Previous
                    </button>
                    <button *ngIf="stepper.selectedIndex < instance.components.length - 1" mat-raised-button
                            color="primary" (click)="nextPage()">
                        Next
                    </button>
                </div>
            </mat-step>
        </mat-horizontal-stepper>`,
    imports: [
        MatStepperModule,
        NgForOf,
        NgIf,
        MatButtonModule
    ],
    standalone: true
})
export class MaterialWizardComponent extends MaterialNestedComponent {
    @ViewChild('stepper', {static: true}) stepper!: MatStepper;
    public isLinear = true;
    private prevNumOfPages = 0;

    setInstance(instance: any) {
        this.isLinear = (!(instance.options &&
            instance.options.breadcrumbSettings &&
            instance.options.breadcrumbSettings.clickable));

        this.updatePages(instance);
        instance.on('pagesChanged', () => this.updatePages());

        super.setInstance(instance);
    }

    updatePages(instance = this.instance) {
        if (this.prevNumOfPages !== instance.pages.length) {
            instance.pages.forEach((page, pageIndex) => {
                page.viewContainer = () => {
                    return this.viewContainers ? this.viewContainers[pageIndex] : null;
                };
            });
            this.prevNumOfPages = instance.pages.length;
        }
    }

    resetWizard() {
        this.instance.cancel();
        this.stepper.reset();
    }

    nextPage() {
        this.instance.nextPage();
        this.stepper.next();
    }

    prevPage() {
        this.instance.prevPage();
        this.stepper.previous();
    }

    submit() {
        this.instance.submit();
    }

    renderComponents() {
        if (this.instance.renderComponents && this.instance.components) {
            this.instance.renderComponents(this.instance.components.reduce((comps, page) => {
                return comps.concat(page.components);
            }, []));
        }
    }
}

Wizard.MaterialComponent = MaterialWizardComponent;
Displays.addDisplay('wizard', Wizard);
export { Wizard };
