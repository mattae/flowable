import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { AngularSplitModule } from 'angular-split';
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';
import { TaskInstance } from '../model/task.model';
import { TaskService } from '../services/task.service';
import { TranslocoModule } from '@ngneat/transloco';
import { NgForOf, NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { AccountService, StylesheetService } from '@mattae/angular-shared';

@Component({
    selector: 'flw-tasks',
    templateUrl: './tasks.component.html',
    standalone: true,
    imports: [
        AngularSplitModule,
        RouterOutlet,
        TranslocoModule,
        NgForOf,
        MatIconModule,
        NgIf,
        MatTooltipModule,
    ],
})
export class TasksComponent implements OnInit {
    tasks: TaskInstance[];
    private currentUserId: string;

    constructor(private _taskService: TaskService,
                private _router: Router,
                private _activatedRoute: ActivatedRoute,
                private _accountService: AccountService,
                private _changeDetectorRef: ChangeDetectorRef) {
        inject(StylesheetService).loadStylesheet('/js/flowable/styles.css');
    }

    ngOnInit(): void {
        this._accountService.getAuthenticationState().subscribe(user => this.currentUserId = user.username);
        this._activatedRoute.data.subscribe(data => {
            const filter = data.filter;
            if (filter) {
                if (filter === 'for-me') {
                    this._taskService.listTasks({assignment: 'assigned'}).subscribe(tasks => {
                        this.tasks = tasks.data;
                        this._changeDetectorRef.markForCheck();
                    });
                } else if (filter === 'completed') {
                    this._taskService.listTasks({state: 'completed'}).subscribe(tasks => {
                        this.tasks = tasks.data;
                        this._changeDetectorRef.markForCheck();
                    });
                } else if (filter === 'open') {
                    this._taskService.listTasks({state: 'active'}).subscribe(tasks => {
                        this.tasks = tasks.data;
                        this._changeDetectorRef.markForCheck();
                    });
                } else if (filter === 'unassigned') {
                    this._taskService.listTasks({assignment: 'unassigned'}).subscribe(tasks => {
                        this.tasks = tasks.data;
                        this._changeDetectorRef.markForCheck();
                    });
                } else {
                    this._taskService.listTasks({}).subscribe(tasks => {
                        this.tasks = tasks.data;
                        this._changeDetectorRef.markForCheck();
                    });
                }
            }
        });
    }

    taskSelected(task: TaskInstance): void {
        this._router.navigate(['../', task.id], {relativeTo: this._activatedRoute})
    }
}
