import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { TranslocoModule } from '@ngneat/transloco';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { TaskInstance } from '../model/task.model';
import { NgIf } from '@angular/common';
import { CaseService } from '../services/case.service';
import { ProcessService } from '../services/process.service';
import { CaseInstance } from '../model/case.model';
import { ProcessInstance } from '../model/process.model';
import { CovalentCommonModule } from '@covalent/core/common';
import { DateTime } from 'luxon';
import { TasksService } from '../services/tasks.service';
import { FormModel, FormOutcome, User } from '../model/common.model';
import { MatFormioModule } from '../../formio/angular-material-formio.module';
import { FuseConfirmationService } from '../../../../../../@fuse/services/confirmation';
import { FormBuilder, FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatMenuModule } from '@angular/material/menu';
import { AdhocTaskComponent } from './adhoc-task.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { map, Observable, startWith } from 'rxjs';
import { UserService } from '../services/user.service';

@Component({
    selector: 'flowable-task',
    templateUrl: './task.component.html',
    standalone: true,
    imports: [
        MatIconModule,
        TranslocoModule,
        RouterLink,
        MatButtonModule,
        MatTabsModule,
        NgIf,
        CovalentCommonModule,
        MatFormioModule,
        ReactiveFormsModule,
        MatMenuModule,
        AdhocTaskComponent,
        MatAutocompleteModule
    ],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class TaskComponent implements OnInit {
    instance: TaskInstance;
    formValid: boolean = false;
    subTasks: TaskInstance[];
    definition: CaseInstance | ProcessInstance;
    form: FormModel;
    formData: any;
    submission: any;
    today = DateTime.now();
    showAdhocTask: boolean = false;
    users: User[];
    userInput = new FormControl<User | null>(null);
    userFilteredOptions: Observable<User[]>;
    showAssigneeInput = false;
    showDueDateInput = false;

    constructor(private _router: Router, private _activatedRoute: ActivatedRoute,
                private _caseService: CaseService, private _processService: ProcessService,
                private _taskService: TasksService,
                private _formBuilder: FormBuilder,
                private _changeDetectorRef: ChangeDetectorRef,
                private _confirmationService: FuseConfirmationService,
                private _userService: UserService) {
    }

    ngOnInit(): void {
        this._activatedRoute.data.subscribe(({instance}) => {
            this.instance = instance;
            if (this.instance.formKey) {
                this._taskService.getTaskForm(this.instance.id).subscribe(form => {
                    this.form = form;

                    this._changeDetectorRef.markForCheck();
                });
            }
            this._taskService.getSubTasks(this.instance.id).subscribe(res => {
                this.subTasks = res;
                this._changeDetectorRef.markForCheck();
            });

            this._changeDetectorRef.markForCheck();
        })
        if (this.instance.caseInstanceId) {
            this._caseService.getCaseInstance(this.instance.caseInstanceId).subscribe(res => {
                this.definition = res;
                this._changeDetectorRef.markForCheck();
            });
        } else if (this.instance.processInstanceId) {
            this._processService.getProcessInstance(this.instance.processInstanceId).subscribe(res => {
                this.definition = res;
                this._changeDetectorRef.markForCheck();
            });
        }

        this._userService.getWorkflowUsers({}).subscribe(res => {
            this.users = res;
            this._changeDetectorRef.markForCheck();
        });

        this.userFilteredOptions = this.userInput.valueChanges.pipe(
            startWith(''),
            map(value => {
                const name = typeof value === 'string' ? value : value?.fullName;
                return name ? this._userFilter(name as string) : [];
            }),
        );

        this._changeDetectorRef.markForCheck();
    }

    executeOutcome(outcome: FormOutcome) {
        this._taskService.completeTaskForm(this.instance.id, {
            outcome: outcome.id,
            values: this.submission,
            formId: this.form.id
        }).subscribe(res => {
            this._taskService.getTask(this.instance.id).subscribe(res => {
                this.instance = res;
                this._changeDetectorRef.markForCheck();
            });
        })
    }

    completeTask() {
        this._taskService.completeTaskForm(this.instance.id, {
            values: this.submission,
            formId: this.form.id
        }).subscribe(res => {
            this._taskService.getTask(this.instance.id).subscribe(res => {
                this.instance = res;
                this._changeDetectorRef.markForCheck();
            });
        })
    }

    saveTask() {
        this._taskService.saveTaskForm(this.instance.id, {
            values: this.submission,
            formId: this.form.id
        }).subscribe(res => {
            this._taskService.getTask(this.instance.id).subscribe(res => {
                this.instance = res;
                this._changeDetectorRef.markForCheck();
            });
        })
    }

    createTask() {
        this.showAdhocTask = true;
    }

    taskCreated() {
        this.showAdhocTask = false;
        this._taskService.getSubTasks(this.instance.id).subscribe(res => {
            this.subTasks = res;
            this._changeDetectorRef.markForCheck();
        });
    }

    claimTask() {
        this._confirmationService.open({
            title: 'Claim Task',
            message: 'Are you sure you want to claim this task?',
            actions: {
                confirm: {
                    show: true,
                    label: 'Claim',
                    color: 'accent'
                },
                cancel: {
                    show: true,
                    label: 'No'
                }
            }
        }).afterClosed().subscribe(res => {
            if (res === 'confirmed') {
                this._taskService.claimTask(this.instance.id).subscribe(res => {
                    this._taskService.getTask(this.instance.id).subscribe(res => {
                        this.instance = res;
                        this._changeDetectorRef.markForCheck();
                    });
                })
            }
        });
    }

    dateSelected(event: any): void {
        this.updateTask(this.instance, {dueDate: event});
        this.showDueDateInput = false;
        this._changeDetectorRef.markForCheck();
    }

    taskSelected(task: TaskInstance): void {
        this._router.navigate(['../../tasks', task.id], {relativeTo: this._activatedRoute})
    }

    updateTask(task: TaskInstance, update: {
        dueDate?: DateTime,
        name?: string,
        description?: string,
        formKey?: string
    }) {
        this._taskService.updateTask(task.id, {
            dueDate: update.dueDate,
            name: update.name,
            description: update.description,
            formKey: update.formKey
        }).subscribe(res => {
                if (task.id === this.instance.id) {
                    this.instance = res;
                    this._changeDetectorRef.markForCheck();
                }
            }
        );
    }

    assigneeClicked() {
        this.showAssigneeInput = !this.showAssigneeInput;
        if (this.userInput.value) {
            this._taskService.assignTask(this.instance.id, this.userInput.value.id).subscribe(res => {
                this._taskService.getTask(this.instance.id).subscribe(res => {
                    this.instance = res;
                    this._changeDetectorRef.markForCheck();
                });
            });
        } else if (this.instance.assignee) {
            this._taskService.removeInvolveTask(this.instance.id, {userId: this.instance.assignee.id})
                .subscribe(res => {
                    this._taskService.getTask(this.instance.id).subscribe(res => {
                        this.instance = res;
                        this._changeDetectorRef.markForCheck();
                    });
                })
        }
        this._changeDetectorRef.markForCheck();
    }

    dueDateClicked() {
        this.showDueDateInput = true;
        this._changeDetectorRef.markForCheck();
    }

    trackValid(event: any) {
        this.formValid = event.isValid;
    }

    userDisplayFn(user: User): string {
        return user && user.fullName ? user.fullName : '';
    }

    private _userFilter(name: string): User[] {
        const filterValue = name.toLowerCase();

        return this.users.filter(option => option.fullName.toLowerCase().includes(filterValue));
    }

    getParentPath(): string[] {
        if (this.instance.caseInstanceId) {
            return [`../../cases/${this.instance.caseInstanceId}`];
        } else {
            return [`../../processes/${this.instance.caseInstanceId}`];
        }
    }
}
