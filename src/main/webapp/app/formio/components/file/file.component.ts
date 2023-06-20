import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';

@Component({
    selector: 'file',
    template: `
        <div *ngIf="visible">
            <mat-label *ngIf="instance.component.label && !instance.component.hideLabel">
                <span>{{instance.component.label}}</span>
            </mat-label>
            <div class="bg-white p-0 rounded shadow">
                <div class="flex items-center justify-center px-6 pt-5 pb-6 border-2 border-gray-300 border-dashed rounded-md">
                    <div class="space-y-1 text-center">
                        <svg class="mx-auto h-12 w-12 text-gray-400" xmlns="http://www.w3.org/2000/svg" fill="none"
                             viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M8 10v4m0 0v4m0-4h4m-4 0H4m12-6a2 2 0 11-4 0 2 2 0 014 0z"/>
                        </svg>
                        <div class="flex text-sm text-gray-600">
                            <label for="file-upload"
                                   class="relative cursor-pointer bg-white rounded-md font-medium text-indigo-600 hover:text-indigo-500 focus-within:outline-none focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-indigo-500">
                                <span>Upload a file</span>
                                <input id="file-upload" name="file-upload" type="file" class="sr-only">
                            </label>
                            <p class="pl-1">or drag and drop</p>
                        </div>
                        <p class="text-xs text-gray-500">
                            PNG, JPG, GIF up to 10MB
                        </p>
                    </div>
                </div>
            </div>
        </div>
    `,
    standalone: true,
    imports: [
        CommonModule,
        MatInputModule,
        ReactiveFormsModule
    ]
})
export class MaterialFileComponent {
    instance: any;
    visible: boolean = true;
    control = new FormControl();
    ngOnInit() {
    }

    setDisabled(disabled) {
    }

    setVisible(visible) {
        this.visible = visible;
    }

    setValue(value) {
    }


    onChange() {
        this.instance.setValue(this.control.value);
    }

    setInstance(instance: any) {
        this.instance = instance;
    }
}
