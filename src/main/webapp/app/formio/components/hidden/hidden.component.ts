import { Component } from '@angular/core';
import { MaterialComponent } from '../MaterialComponent';
import HiddenComponent from 'formiojs/components/hidden/Hidden.js';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';

@Component({
    selector: 'mat-formio-hidden',
    template: `<input matInput type="hidden" [formControl]="control" #input>`,
    imports: [
        ReactiveFormsModule,
        MatInputModule
    ],
    standalone: true
})
export class MaterialHiddenComponent extends MaterialComponent {
}

HiddenComponent.MaterialComponent = MaterialHiddenComponent;
export { HiddenComponent };
