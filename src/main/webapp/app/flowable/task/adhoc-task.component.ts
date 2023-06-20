import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    EventEmitter,
    Input,
    OnInit,
    Output,
    ViewChild
} from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { AsyncPipe, NgForOf, NgIf } from '@angular/common';
import { TranslocoModule } from '@ngneat/transloco';
import { DateTime } from 'luxon';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { map, Observable, startWith } from 'rxjs';
import { FormService } from '../services/form.service';
import { FormDefinition } from '../model/form.model';
import { TasksService } from '../services/tasks.service';
import { TaskInstance } from '../model/task.model';
import { CovalentTextEditorModule } from '@covalent/text-editor';
import { FuseAlertComponent } from '../../../../../../@fuse/components/alert';
import { UserService } from '../services/user.service';
import { User } from '../model/common.model';

@Component({
    selector: 'adhoc-task',
    template: `
        <div class="flex flex-col p-4">
            <fuse-alert
                    *ngIf="showAlert"
                    [appearance]="'outline'"
                    [showIcon]="false"
                    [type]="'error'">
                {{'FLOWABLE.TASK.SUBTASK.FORM_KEY_EXISTS' | transloco}}
            </fuse-alert>
            <mat-form-field>
                <mat-label>{{'FLOWABLE.TASK.SUBTASK.FORM_DEFINITION' | transloco}}</mat-label>
                <input type="text" [formControl]="input" matInput [matAutocomplete]="auto">
                <mat-autocomplete #auto="matAutocomplete" [displayWith]="displayFn">
                    <mat-option *ngFor="let option of filteredOptions | async" [value]="option">
                        {{option.name}}
                    </mat-option>
                </mat-autocomplete>
                <span matIconSuffix [matTooltip]="'FLOWABLE.TASK.ATTACH_FORM' | transloco">
                    <mat-icon *ngIf="!file" (click)="click(upload)">add</mat-icon>
                    <mat-icon *ngIf="file" (click)="click(upload)">attachment</mat-icon>
                </span>
            </mat-form-field>
            <form [formGroup]="formGroup">
                <div class="flex flex-col md:flex-row">
                    <div class="w-full md:w-2/3">
                        <div class="mt-8">
                            <mat-form-field
                                    class="w-full"
                                    [subscriptSizing]="'dynamic'">
                                <mat-label>{{'FLOWABLE.TASK.SUBTASK.NAME' | transloco}}</mat-label>
                                <input
                                        matInput
                                        required
                                        [formControlName]="'name'"
                                        [spellcheck]="false">
                                <mat-error
                                        *ngIf="formGroup.controls['name'].hasError('required')">
                                    {{'FLOWABLE.TASK.SUBTASK.NAME_REQUIRED' | transloco }}
                                </mat-error>
                            </mat-form-field>
                        </div>
                    </div>
                    <div class="w-full md:w-1/3">
                        <div class="mt-8">
                            <mat-form-field
                                    class="w-full"
                                    [subscriptSizing]="'dynamic'">
                                <mat-label>{{'FLOWABLE.TASK.SUBTASK.ASSIGNEE' | transloco}}</mat-label>
                                <input type="text" [formControl]="userInput" matInput [matAutocomplete]="auto1">
                                <mat-autocomplete #auto1="matAutocomplete" [displayWith]="userDisplayFn">
                                    <mat-option *ngFor="let option of userFilteredOptions | async" [value]="option">
                                        {{option.fullName}}
                                    </mat-option>
                                </mat-autocomplete>
                            </mat-form-field>
                        </div>
                    </div>
                    <div class="w-full md:w-1/3">
                        <div class="mt-8">
                            <mat-form-field
                                    class="w-full"
                                    [subscriptSizing]="'dynamic'">
                                <mat-label>{{'FLOWABLE.TASK.SUBTASK.DUE_DATE' | transloco}}</mat-label>
                                <input matInput [matDatepicker]="date"
                                       formControlName="dueDate"
                                       [min]="today">
                                <mat-datepicker-toggle
                                        matSuffix
                                        [for]="date">
                                </mat-datepicker-toggle>
                                <mat-datepicker #date></mat-datepicker>
                            </mat-form-field>
                        </div>
                    </div>
                </div>
                <div class="mt-8">
                    <mat-label>{{'FLOWABLE.TASK.SUBTASK.DESCRIPTION' | transloco}}</mat-label>
                    <td-text-editor class="pt-2" [formControlName]="'description'"></td-text-editor>
                </div>
                <div class="flex flex-row justify-end pt-4 gap-x-1.5">
                    <button (click)="saveTask()"
                            mat-raised-button
                            [disabled]="!formGroup.valid"
                            color="primary">
                        {{'FLOWABLE.TASK.SAVE_SUBTASK' | transloco}}
                    </button>

                    <button (click)="cancelTask()"
                            *ngIf="!embedded"
                            mat-raised-button>
                        {{'FLOWABLE.TASK.CANCEL_SUBTASK' | transloco}}
                    </button>
                </div>
            </form>
        </div>

        <input #upload type='file' class="hidden" accept=".json,.form"
               (change)="attachForm($event)"/>
    `,
    standalone: true,
    imports: [
        FormsModule,
        MatButtonModule,
        MatDatepickerModule,
        MatFormFieldModule,
        MatInputModule,
        MatOptionModule,
        MatSelectModule,
        NgIf,
        ReactiveFormsModule,
        TranslocoModule,
        AsyncPipe,
        MatAutocompleteModule,
        MatIconModule,
        NgForOf,
        MatTooltipModule,
        CovalentTextEditorModule,
        FuseAlertComponent
    ],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class AdhocTaskComponent implements OnInit {
    @Input()
    task: TaskInstance;
    @Input()
    embedded: boolean = false;
    @Output()
    taskCreated = new EventEmitter();
    @ViewChild('upload') fileInput: any;
    file!: File;
    showAlert = false;
    formGroup: FormGroup;
    today = DateTime.now();
    formDefinitions: FormDefinition[]
    users: User[];
    selectedFormDefinition: FormDefinition;
    input = new FormControl<FormDefinition | null>(null);
    filteredOptions: Observable<FormDefinition[]>;
    userInput = new FormControl<User | null>(null);
    userFilteredOptions: Observable<User[]>;

    constructor(private _formBuilder: FormBuilder, private _formService: FormService,
                private _changeDetectorRef: ChangeDetectorRef, private _taskService: TasksService,
                private _userService: UserService) {
        this.formGroup = this._formBuilder.group({
            name: new FormControl<string | null>('', {
                validators: [Validators.required]
            }),
            description: new FormControl<string | null>(''),
            dueDate: new FormControl<DateTime | null>(null),
            assignee: FormControl<string | null>
        });
    }


    ngOnInit(): void {
        this._formService.getFormDefinitions().subscribe(res => {
            this.formDefinitions = res.data;
            this._changeDetectorRef.markForCheck();
        });

        this._userService.getWorkflowUsers({}).subscribe(res => {
            this.users = res;
            this._changeDetectorRef.markForCheck();
        });

        this.filteredOptions = this.input.valueChanges.pipe(
            startWith(''),
            map(value => {
                const name = typeof value === 'string' ? value : value?.name;
                return name ? this._filter(name as string) : [];
            }),
        );

        this.userFilteredOptions = this.userInput.valueChanges.pipe(
            startWith(''),
            map(value => {
                const name = typeof value === 'string' ? value : value?.fullName;
                return name ? this._userFilter(name as string) : [];
            }),
        );

        this._changeDetectorRef.markForCheck();
    }

    click(input: HTMLInputElement) {
        input.value = ''
        this.showAlert = false;
        this._changeDetectorRef.markForCheck();
        this.fileInput.nativeElement.click();
    }

    async attachForm(event) {
        this.file = event.target.files[0];
        if (this.file) {
            const text = await this.file.text()
            const formKey = JSON.parse(text)['key'];
            if (formKey) {
                const exist = !!this.formDefinitions.find(f => f.key === formKey);
                if (exist) {
                    this.showAlert = true;
                    this._changeDetectorRef.markForCheck();
                    return;
                }
                const formData = new FormData();
                formData.append('file', this.file);
                this._formService.saveFormDefinition(formData).subscribe(res => {
                    const deployment = res;
                    this._formService.getFormDefinitions().subscribe(res => {
                        this.formDefinitions = res.data;

                        this.selectedFormDefinition = this.formDefinitions.find(f => f.deploymentId === deployment.id);
                        this.input.setValue(this.selectedFormDefinition);
                        this._changeDetectorRef.markForCheck();
                    });
                });
            }
        }
    }

    saveTask() {
        const value = this.formGroup.value;
        this._taskService.createTask({
            parentTaskId: this.task?.id,
            name: value.name,
            description: value.description,
            assignee: value.assignee
        }).subscribe(res => {
            if (value.dueDate || this.input.value) {
                this.updateTask(res, {
                    //dueDate: value.dueDate,
                    formKey: this.input.value?.key
                });
            }
            this.formGroup.reset();
            this.taskCreated.emit();
        })
    }

    cancelTask() {

    }

    updateTask(task: TaskInstance, update: {
        dueDate?: DateTime,
        name?: string,
        description?: string,
        formKey?: string
    }) {
        this._taskService.updateTask(task?.id, {
            dueDate: update.dueDate,
            name: update.name,
            description: update.description,
            formKey: update.formKey
        }).subscribe();
    }

    displayFn(formDefinition: FormDefinition): string {
        return formDefinition && formDefinition.name ? formDefinition.name : '';
    }

    userDisplayFn(user: User): string {
        return user && user.fullName ? user.fullName : '';
    }

    private _filter(name: string): FormDefinition[] {
        const filterValue = name.toLowerCase();

        return this.formDefinitions.filter(option => option.name.toLowerCase().includes(filterValue));
    }

    private _userFilter(name: string): User[] {
        const filterValue = name.toLowerCase();

        return this.users.filter(option => option.fullName.toLowerCase().includes(filterValue));
    }
}
