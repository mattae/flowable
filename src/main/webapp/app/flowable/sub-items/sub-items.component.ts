import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { TaskService } from '../services/task.service';
import { TaskInstance } from '../model/task.model';
import { MatIconModule } from '@angular/material/icon';
import { NgForOf, NgIf } from '@angular/common';
import { TranslocoModule } from '@ngneat/transloco';
import { CovalentCommonModule } from '@covalent/core/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
    selector: 'flw-sub-items',
    templateUrl: './sub-items.component.html',
    standalone: true,
    imports: [
        MatIconModule,
        NgIf,
        TranslocoModule,
        NgForOf,
        CovalentCommonModule,
        MatTooltipModule
    ],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class SubItemsComponent implements OnInit {
    @Input()
    caseInstanceId: string;
    @Input()
    processInstanceId: string;
    @Input()
    taskId: string;
    @Input()
    activeTasks: boolean;
    tasks: TaskInstance[];

    constructor(private _taskService: TaskService,
                private _router: Router,
                private _activatedRoute: ActivatedRoute,
                private _changeDetectorRef: ChangeDetectorRef) {
    }

    ngOnInit(): void {
        if (this.activeTasks) {
            this.listActiveTasks();
        } else {
            this.listAllTasks();
        }
    }

    listAllTasks() {
        if (this.caseInstanceId) {
            this._taskService.listTasks({caseInstanceId: this.caseInstanceId}).subscribe(res => {
                this.tasks = res.data;
                this._changeDetectorRef.markForCheck();
            });
        } else if (this.processInstanceId) {
            this._taskService.listTasks({processInstanceId: this.processInstanceId}).subscribe(res => {
                this.tasks = res.data;
                this._changeDetectorRef.markForCheck();
            });
        } else if (this.taskId) {
            this._taskService.getSubTasks(this.taskId).subscribe(res => {
                this.tasks = res;
                this._changeDetectorRef.markForCheck();
            });
        }
    }

    listActiveTasks(): void {
        if (this.caseInstanceId) {
            this._taskService.listTasks({caseInstanceId: this.caseInstanceId, state: 'active'}).subscribe(res => {
                this.tasks = res.data;
                this._changeDetectorRef.markForCheck();
            });
        } else if (this.processInstanceId) {
            this._taskService.listTasks({processInstanceId: this.processInstanceId, state: 'active'}).subscribe(res => {
                this.tasks = res.data;
                this._changeDetectorRef.markForCheck();
            });
        }
    }

    taskSelected(task: TaskInstance): void {
        this._router.navigate(['../../tasks', task.id], {relativeTo: this._activatedRoute})
    }
}
