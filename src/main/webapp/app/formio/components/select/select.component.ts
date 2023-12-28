import { ChangeDetectorRef, Component, ElementRef, OnInit } from '@angular/core';
import { MaterialComponent } from '../MaterialComponent';
import SelectComponent from 'formiojs/components/select/Select.js';
import _ from 'lodash';
import { HttpClient } from '@angular/common/http';
import { FormioFormFieldComponent } from '../formio-form-field/formio-form-field.component';
import { MatSelectModule } from '@angular/material/select';
import { AsyncPipe, NgForOf, NgIf } from '@angular/common';
import { LabelComponent } from '../label/label.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TranslocoModule } from '@ngneat/transloco';

@Component({
    selector: 'mat-formio-select',
    template: `
        <mat-formio-form-field [instance]="instance" [componentTemplate]="componentTemplate"></mat-formio-form-field>
        <ng-template #componentTemplate let-hasLabel>
            <mat-form-field class="w-full">

                <mat-label *ngIf="hasLabel">
                    <span [instance]="instance" matFormioLabel></span>
                </mat-label>

                <span *ngIf="instance.component.prefix" matPrefix>
                    {{ instance.component.prefix }}&nbsp;
                </span>
                <mat-select
                        [multiple]="instance.component.multiple"
                        [formControl]="control"
                        [placeholder]="instance.component.placeholder | transloco"
                        (selectionChange)="onChange()"
                        [compareWith]="compareObjects"
                >
                    <div class="mat-option m-3.5 border ring-1 ring-primary-100">
                        <input class="m-2 mat-input-element" placeholder="Type to search"
                               (input)="onFilter($event.target)">
                    </div>
                    <mat-option *ngIf="!filteredOptionsLength" disabled>
                        <span>Nothing was found</span>
                    </mat-option>
                    <mat-option *ngFor="let option of filteredOptions | async" [value]="option.value">
                        <span [innerHTML]="option.label"></span>
                    </mat-option>
                </mat-select>

                <span *ngIf="instance.component.suffix" matSuffix>
                    {{ instance.component.suffix }}
                </span>
                <mat-error *ngIf="instance.error">{{ instance.error.message | transloco}}</mat-error>
            </mat-form-field>
        </ng-template>
    `,
    imports: [
        FormioFormFieldComponent,
        MatSelectModule,
        NgIf,
        LabelComponent,
        ReactiveFormsModule,
        TranslocoModule,
        AsyncPipe,
        NgForOf
    ],
    standalone: true
})
export class MaterialSelectComponent extends MaterialComponent implements OnInit {
    selectOptions!: Promise<any[]>;
    filteredOptions!: Promise<any[]>;
    filteredOptionsLength!: number;

    selectOptionsResolve: any;

    constructor(public element: ElementRef, public ref: ChangeDetectorRef, private http: HttpClient) {
        super(element, ref);
    }

    setInstance(instance: any) {
        super.setInstance(instance);
        this.instance.triggerUpdate();
    }

    ngOnInit() {
        if (this.instance.component.dataSrc && this.instance.component.data && this.instance.component.data.url) {
            this.http.get(this.instance.component.data.url).subscribe(res=>{
                this.instance.setItems(res);
            })
        }
        this.selectOptions = new Promise((resolve) => {
            this.selectOptionsResolve = resolve;
        });
        this.selectOptions.then((options) => {
            this.filteredOptionsLength = options.length;
        });

        this.filteredOptions = this.selectOptions;
    }

    onFilter(event: any) {
        const value = event.value.toLowerCase().trim();
        this.filteredOptions = this.selectOptions.then((options) => {
            const filtered = options.filter((option) => option.label?.toLowerCase().indexOf(value) !== -1);
            this.filteredOptionsLength = filtered.length;
            return filtered;
        });
    }

    compareObjects(o1: any, o2: any): boolean {
        return _.isEqual(o1, o2);
    }
}

SelectComponent.MaterialComponent = MaterialSelectComponent;

// Make sure we detect changes when new items make their way into the select dropdown.
const setItems = SelectComponent.prototype.setItems;
SelectComponent.prototype.setItems = function (...args) {
    setItems.call(this, ...args);
    if (this.materialComponent && this.materialComponent.selectOptionsResolve) {
        this.materialComponent.selectOptionsResolve(this.selectOptions);
    }
};

export { SelectComponent };