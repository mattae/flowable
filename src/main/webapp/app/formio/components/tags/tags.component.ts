import { Component } from '@angular/core';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import TagsComponent from 'formiojs/components/tags/Tags.js';
import { MaterialComponent } from '../MaterialComponent';
import { MatChipInputEvent, MatChipsModule } from '@angular/material/chips';
import { FormioFormFieldComponent } from '../formio-form-field/formio-form-field.component';
import { NgForOf, NgIf } from '@angular/common';
import { LabelComponent } from '../label/label.component';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { TranslocoModule } from '@ngneat/transloco';

@Component({
    selector: 'mat-formio-tags',
    template: `
        <mat-formio-form-field [instance]="instance" [componentTemplate]="componentTemplate"></mat-formio-form-field>
        <ng-template #componentTemplate let-hasLabel>
            <mat-form-field class="example-chip-list w-full h-full">

                <mat-label *ngIf="hasLabel">
                    <span [instance]="instance" matFormioLabel></span>
                </mat-label>

                <mat-chip-grid #chipList [attr.aria-label]="instance.component.label | transloco">
                    <mat-chip-option *ngFor="let tag of tags; index as i;"
                                     [selectable]="true"
                                     [removable]="!instance.disabled"
                                     (removed)="remove(i)"
                    >
                        {{tag | transloco}}
                        <mat-icon matChipRemove>cancel</mat-icon>
                    </mat-chip-option>

                    <input [formControl]="control"
                           [matChipInputFor]="chipList"
                           [matChipInputSeparatorKeyCodes]="separatorKeysCodes"
                           [matChipInputAddOnBlur]="true"
                           (matChipInputTokenEnd)="add($event)"
                    >
                </mat-chip-grid>
            </mat-form-field>
        </ng-template>
    `,
    standalone: true,
    imports: [
        FormioFormFieldComponent,
        MatChipsModule,
        NgForOf,
        LabelComponent,
        NgIf,
        MatIconModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        TranslocoModule
    ]
})
export class MaterialTagsComponent extends MaterialComponent {
    readonly separatorKeysCodes: number[] = [ENTER, COMMA];
    tags: string[] = [];

    add(event: MatChipInputEvent): void {
        const input = event.input;
        const value = event.value;
        if ((value || '').trim()) {
            this.tags.push(value.trim());
        }
        if (input) {
            input.value = '';
        }
        this.onChange();
    }

    remove(index): void {
        if (index >= 0 && index < this.tags.length) {
            this.tags.splice(index, 1);
        }
        this.onChange();
    }

    getValue() {
        return (this.instance.component.storeas === 'string') ? this.tags.join(this.instance.delimiter) : this.tags;
    }

    setValue(value) {
        console.log('Component', this.instance)
        if (typeof value === 'string') {
            value = value.split(this.instance.delimiter);
        }
        if (value && !Array.isArray(value)) {
            value = [value];
        }
        this.tags = value;
    }
}

(TagsComponent as any).MaterialComponent = MaterialTagsComponent;
export { TagsComponent };
