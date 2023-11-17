import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { AsyncPipe, NgForOf, NgIf } from '@angular/common';
import { UserService } from '../services/user.service';
import { User } from '../model/common.model';
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatOptionModule } from '@angular/material/core';
import { Observable, of, startWith, switchMap } from 'rxjs';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { TranslocoModule } from '@ngneat/transloco';
import { ProcessService } from '../services/process.service';
import { CaseService } from '../services/case.service';
import { TaskService } from '../services/task.service';

@Component({
    selector: 'flw-people',
    templateUrl: './people.component.html',
    standalone: true,
    imports: [
        FormsModule,
        MatChipsModule,
        MatIconModule,
        NgForOf,
        ReactiveFormsModule,
        AsyncPipe,
        MatAutocompleteModule,
        MatButtonModule,
        MatFormFieldModule,
        MatOptionModule,
        TranslocoModule,
        NgIf
    ],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class PeopleComponent implements OnInit {
    @Input()
    processInstanceId: string;
    @Input()
    taskId: string;
    @Input()
    caseInstanceId: string;
    @Input()
    assignee: User;
    @Input()
    readonly = false;
    input = new FormControl<User | null>(null);
    users: User[];
    users$: Observable<User[]>;
    filteredUsers: User[] = [];
    participants: User[] = [];
    addOnBlur = true;
    readonly separatorKeysCodes = [ENTER, COMMA] as const;

    constructor(private _userService: UserService, private _caseService: CaseService,
                private _processService: ProcessService, private _taskService: TaskService,
                private _changeDetectorRef: ChangeDetectorRef) {
    }

    ngOnInit(): void {
        this.users$ = this.input.valueChanges.pipe(
            startWith(''),
            switchMap(value => {
                const name = typeof value === 'string' ? value : value?.fullName;
                return name ? this._userService.getWorkflowUsers({
                    filter: name,
                    excludeCaseId: this.caseInstanceId,
                    excludeProcessId: this.processInstanceId,
                    excludeTaskId: this.taskId
                }) : of([]);
            }),
        );

        if (this.taskId) {
            this._taskService.getInvolvedUsers(this.taskId).subscribe(res => this.participants = res)
        } else if (this.processInstanceId) {
            this._processService.getInvolvedUsers(this.processInstanceId).subscribe(res => this.participants = res)
        } else if (this.caseInstanceId) {
            this._caseService.getInvolvedUsers(this.caseInstanceId).subscribe(res => this.participants = res)
        }

        this._changeDetectorRef.markForCheck();
    }

    add(event: MatAutocompleteSelectedEvent): void {
        const value: User = event.option.value;

        // Add our user
        if (value) {
            const user = this.users.find(user => user.fullName.toLowerCase().includes(value.fullName.toLowerCase()));
            if (user) {
                this.filteredUsers.push(user);
                this.participants.push(user);

                this._changeDetectorRef.markForCheck();
            }
        }
    }

    remove(user: User): void {
        let index = this.filteredUsers.indexOf(user);

        if (index >= 0) {
            this.filteredUsers.splice(index, 1);
        }
        index = this.participants.indexOf(user);

        if (index >= 0) {
            this.participants.splice(index, 1);
        }

        this._changeDetectorRef.markForCheck();
    }

    addCandidates() {
        this.filteredUsers.forEach(user => {
            if (this.taskId) {
                this._taskService.involveTask(this.taskId, user.id)
                    .subscribe(_=> this.participants.push(user));
            } else if (this.processInstanceId) {
                this._processService.involveProcess(this.processInstanceId, user.id)
                    .subscribe(_=> this.participants.push(user));
            } else if (this.caseInstanceId) {
                this._caseService.involveCase(this.caseInstanceId, user.id)
                    .subscribe(_=> this.participants.push(user));
            }
        });

        this._changeDetectorRef.markForCheck();
    }

    removeCandidate(user: User): void {
        if (this.taskId) {
            this._taskService.removeInvolveTask(this.taskId, {userId: user.id}).subscribe(res => {
                this.remove(user);
            });
        } else if (this.processInstanceId) {
            this._processService.removeInvolveProcess(this.processInstanceId, {userId: user.id}).subscribe(res => {
                this.remove(user);
            });
        } else if (this.caseInstanceId) {
            this._caseService.removeInvolveCase(this.caseInstanceId, {userId: user.id}).subscribe(res => {
                this.remove(user);
            });
        }

        this._changeDetectorRef.markForCheck();
    }

    getInitials(user: User) {
        return user.firstName.charAt(0).toUpperCase() + user.lastName.charAt(0).toUpperCase();
    }

    displayFn(user: User): string {
        return user && user.fullName ? user.fullName : '';
    }
}
