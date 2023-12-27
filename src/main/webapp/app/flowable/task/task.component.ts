import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { TranslocoModule } from '@ngneat/transloco';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { TaskInstance } from '../model/task.model';
import { NgIf } from '@angular/common';
import { CovalentCommonModule } from '@covalent/core/common';
import { DateTime } from 'luxon';
import { TaskService } from '../services/task.service';
import { FormModel, FormOutcome, User } from '../model/common.model';
import { FormBuilder, FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatMenuModule } from '@angular/material/menu';
import { AdhocTaskComponent } from './adhoc-task.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { Observable, startWith, switchMap } from 'rxjs';
import { UserService } from '../services/user.service';
import { PeopleComponent } from '../people/people.component';
import { DocumentsComponent } from '../documents/documents.component';
import { CommentsComponent } from '../comments/comments.component';
import { SubItemsComponent } from '../sub-items/sub-items.component';
import { AccountService, FuseConfirmationService, MatFormioComponent } from '@mattae/angular-shared';
import { FormService } from '../services/form.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';

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
        ReactiveFormsModule,
        MatMenuModule,
        AdhocTaskComponent,
        MatAutocompleteModule,
        PeopleComponent,
        DocumentsComponent,
        CommentsComponent,
        SubItemsComponent,
        MatFormFieldModule,
        MatInputModule,
        MatFormioComponent,
        MatDatepickerModule
    ],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class TaskComponent implements OnInit {
    @ViewChild('subTasks') subTaskGroup: MatTabGroup
    instance: TaskInstance;
    formValid: boolean = true;
    subTasks: TaskInstance[];
    form: FormModel;
    formData: any;
    comments: number;
    submission: any;
    savedSubmission: any;
    today = DateTime.now();
    showAdhocTask: boolean = false;
    users: User[];
    currentUserId: string;
    userInput = new FormControl<User | null>(null);
    userFilteredOptions: Observable<User[]>;
    showAssigneeInput = false;
    showDueDateInput = false;
    selectedTab = 0;

    constructor(private _router: Router,
                private _activatedRoute: ActivatedRoute,
                private _taskService: TaskService,
                private _formBuilder: FormBuilder,
                private _changeDetectorRef: ChangeDetectorRef,
                private _confirmationService: FuseConfirmationService,
                private _userService: UserService,
                private _accountService: AccountService,
                private _formService: FormService) {
    }

    ngOnInit(): void {
        this._accountService.getAuthenticationState().subscribe(res => {
            if (res) {
                this.currentUserId = res.username;
            }
            this._changeDetectorRef.markForCheck();
        });
        this._activatedRoute.data.subscribe(({instance}) => {
            this.instance = instance;
            if (this.instance.formKey) {
                this._taskService.getTaskForm(this.instance.id).subscribe(form => {
                    this.form = form;
                    this._formService.getFormData({
                        formDefinitionKey: form.key,
                        taskId: instance.id
                    }).subscribe(data => {
                        this.savedSubmission = {
                            data: data
                        };
                        this._changeDetectorRef.markForCheck();
                    });
                });
            }
            this._taskService.getSubTasks(this.instance.id).subscribe(res => {
                this.subTasks = res;
                this._changeDetectorRef.markForCheck();
            });

            this._changeDetectorRef.markForCheck();
        })

        this._userService.getWorkflowUsers({}).subscribe(res => {
            this.users = res;
            this._changeDetectorRef.markForCheck();
        });

        this.userFilteredOptions = this.userInput.valueChanges.pipe(
            startWith(''),
            switchMap(value => {
                    const name = typeof value === 'string' ? value : value?.fullName;
                    return name ? this._userService.getWorkflowUsers({filter: name, excludeTaskId: this.instance.id}) : [];
                }
            ),
        );

        this._changeDetectorRef.markForCheck();
    }

    commentCount(count) {
        this.comments = count;
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
        if (this.instance.formKey) {
            this._taskService.completeTaskForm(this.instance.id, {
                values: this.submission,
                formId: this.form.id
            }).subscribe(res => {
                this._taskService.getTask(this.instance.id).subscribe(res => {
                    this.instance = res;
                    this._changeDetectorRef.markForCheck();
                });
            });
        } else {
            this._taskService.completeTask(this.instance.id).subscribe(res => {
                this._taskService.getTask(this.instance.id).subscribe(res => {
                    this.instance = res;
                    this._changeDetectorRef.markForCheck();
                });
            });
        }
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
        this._changeDetectorRef.markForCheck();
    }

    taskCreated() {
        this.showAdhocTask = false;
        this._taskService.getSubTasks(this.instance.id).subscribe(res => {
            this.subTasks = res;

            this.subTaskGroup.selectedIndex = 0;
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

    unClaimTask() {
        this._confirmationService.open({
            title: 'Un-claim Task',
            message: 'Are you sure you want to un-claim this task?',
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
                this._taskService.unClaimTask(this.instance.id).subscribe(res => {
                    this._taskService.getTask(this.instance.id).subscribe(res => {
                        this.instance = res;
                        this._changeDetectorRef.markForCheck();
                    });
                })
            }
        });
    }

    tabChanged(index: number) {
        this.selectedTab = index;
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

    /**
     * Does the task have an assignee
     */
    public hasAssignee(): boolean {
        return !!this.instance.assignee;
    }

    /**
     * Returns true if the task is assigned to logged in user
     */
    public isAssignedTo(userId: string): boolean {
        return this.hasAssignee() ? this.instance.assignee.id === userId : false;
    }

    /**
     * Return true if the task assigned
     */
    public isAssignedToCurrentUser(): boolean {
        return this.hasAssignee() && this.isAssignedTo(this.currentUserId);
    }

    /**
     * Return true if the user is a candidate member
     */
    isCandidateMember(): boolean {
        return this.instance.memberOfCandidateGroup || this.instance.memberOfCandidateUsers;
    }

    /**
     * Return true if the task claimable
     */
    public isTaskClaimable(): boolean {
        return !this.hasAssignee() && this.isCandidateMember();
    }

    /**
     * Return true if the task claimed by candidate member.
     */
    public isTaskClaimedByCandidateMember(): boolean {
        return this.isCandidateMember() && this.isAssignedToCurrentUser() && !this.isCompleted();
    }

    /**
     * Returns task's status
     */
    getTaskStatus(): string {
        return (this.instance && this.instance.endDate) ? 'Completed' : 'Running';
    }

    /**
     * Returns true if the task is completed
     */
    isCompleted(): boolean {
        return this.instance && !!this.instance.endDate;
    }

    trackValid(event: any) {
        if (event.data) {
            this.formValid = event.isValid;
            this.submission = event.data;
        }
    }

    userDisplayFn(user: User): string {
        return user && user.fullName ? user.fullName : '';
    }

    getParentPath(): string[] {
        if (this.instance.caseInstanceId) {
            return [`/work/cases/${this.instance.caseInstanceId}`];
        } else if (this.instance.processInstanceId) {
            return [`/work/processes/${this.instance.processInstanceId}`];
        } else {
            return [`/tasks/${this.instance.parentTaskId}`];
        }
    }
}
