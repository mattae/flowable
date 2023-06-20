import { Component } from '@angular/core';
import { MaterialComponent } from '../MaterialComponent';
import SurveyComponent from 'formiojs/components/survey/Survey.js';
import { FormControl } from '@angular/forms';

@Component({
    selector: 'mat-formio-survey',
    template: `

        <mat-formio-form-field
                [instance]="instance"
                [componentTemplate]="componentTemplate"
                [showDescription]="false"
        ></mat-formio-form-field>
        <ng-template #componentTemplate let-hasLabel>
            <div class="rounded ring-1 ring-primary-100 ring-inset p-3">
                <table class="border-collapse table-auto border w-full h-full p-4 border-slate-400">
                    <thead class="bg-slate-50 dark:bg-slate-700">
                    <tr>
                        <th class="border border-slate-340 dark:border-slate-600 font-semibold p-4 text-slate-900 dark:text-slate-200">
                            <h2 *ngIf="hasLabel">
                                <span [instance]="instance" matFormioLabel></span>
                            </h2>
                        </th>
                        <th class="border border-slate-340 dark:border-slate-600 font-semibold p-4 text-slate-900 dark:text-slate-200"
                            *ngFor="let value of instance.component.values"
                        >
                            {{ value.label }}
                        </th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr *ngFor="let question of instance.component.questions; index as i;">
                        <td class="border border-slate-300 dark:border-slate-700 p-2 text-slate-500 dark:text-slate-400">{{ question.label }}</td>
                        <td class="border border-slate-300 dark:border-slate-700 p-2 text-slate-500 dark:text-slate-400"
                            *ngFor="let value of instance.component.values; index as j;"
                        >
                            <div class="flex items-center justify-center">
                                <mat-radio-group (change)="onChange()"
                                                 [formControl]="getFormControl(question.value)"
                                                 [name]="getUniqueName(question.value)"
                                >
                                    <mat-radio-button [value]="value.value"></mat-radio-button>
                                </mat-radio-group>
                            </div>
                        </td>
                    </tr>
                    <mat-hint *ngIf="instance.component.description" class="mat-formio-component-description">
                        {{ instance.component.description }}
                    </mat-hint>
                    </tbody>

                    <mat-error *ngIf="instance.error">{{ instance.error.message }}</mat-error>
                </table>
            </div>
        </ng-template>
    `
})
export class MaterialSurveyComponent extends MaterialComponent {
  public controls: any = {};
  getFormControl(question) {
    if (!this.controls[question]) {
      this.controls[question] = new FormControl();
      if (this.instance.shouldDisabled) {
        this.controls[question].disable();
      }
    }
    return this.controls[question];
  }

  setDisabled(disabled) {
    const method = disabled ? 'disable' : 'enable';
    for (const question in this.controls) {
      if (this.controls.hasOwnProperty(question)) {
        this.controls[question][method]();
      }
    }
  }

  getValue() {
    const values = {};
    for (const question in this.controls) {
      if (this.controls.hasOwnProperty(question)) {
        values[question] = this.controls[question].value || false;
      }
    }
    return values;
  }

  setValue(value) {
    for (const question in value) {
      if (value.hasOwnProperty(question)) {
        const control = this.getFormControl(question);
        if (control) {
          control.setValue(value[question] || false);
        }
      }
    }
  }

  getUniqueName(question) {
    return `${this.instance.id}-${question}`;
  }
}
SurveyComponent.MaterialComponent = MaterialSurveyComponent;
export { SurveyComponent };
