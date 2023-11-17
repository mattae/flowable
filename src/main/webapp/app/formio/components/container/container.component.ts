import { Component } from '@angular/core';
import { MaterialNestedComponent } from '../MaterialNestedComponent';
import ContainerComponent from 'formiojs/components/container/Container.js';

@Component({
    selector: 'mat-formio-container',
    template: `
        <div class="flex flex-col gap-1">
            <ng-template #components></ng-template>
        </div>`,
    standalone: true
})
export class MaterialContainerComponent extends MaterialNestedComponent {
}

ContainerComponent.MaterialComponent = MaterialContainerComponent;
export { ContainerComponent };
