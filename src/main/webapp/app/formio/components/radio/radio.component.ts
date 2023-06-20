import { Component } from '@angular/core';
import { MaterialComponent } from '../MaterialComponent';
import RadioComponent from 'formiojs/components/radio/Radio.js';

@Component({
  selector: 'mat-formio-radio',
  template: `
      <mat-formio-form-field [instance]="instance" [componentTemplate]="componentTemplate"></mat-formio-form-field>
      <ng-template #componentTemplate let-hasLabel>
          <div class="flex-col flex">
              <mat-label *ngIf="hasLabel">
                  <span [instance]="instance" matFormioLabel></span>
              </mat-label>

              <mat-radio-group
                      (change)="onChange()"
                      [formControl]="control"
                      class="flex pl-2"
                      [ngClass]="getLayout()"
              >
                  <mat-radio-button *ngFor="let option of instance.component.values"
                                    value="{{ option.value }}"
                                    [checked]="isRadioChecked(option)"
                                    (keyup.space)="clearValue($event, option)"
                                    (click)="clearValue($event, option)"
                  >
                      {{ option.label }}
                  </mat-radio-button>
                  <mat-error *ngIf="instance.error">{{ instance.error.message }}</mat-error>
              </mat-radio-group>
          </div>
      </ng-template>
  `
})
export class MaterialRadioComponent extends MaterialComponent {
  getLayout() {
    return this.instance.component.inline ? 'flex-row' : 'flex-col';
  }

  isRadioChecked(option) {
    return option.value === this.instance.dataValue;
  }

  clearValue(event, option) {
    if (this.isRadioChecked(option)) {
      event.preventDefault();
      this.deselectValue();
    }
  }

  deselectValue() {
    this.instance.updateValue(null, {
      modified: true,
    });
  }
}
RadioComponent.MaterialComponent = MaterialRadioComponent;
export { RadioComponent };
