import { Component } from '@angular/core';
import { AdhocTaskComponent } from '../task/adhoc-task.component';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TaskInstance } from '../model/task.model';

@Component({
    selector: 'new-task',
    templateUrl: './new-task.component.html',
    imports: [
        AdhocTaskComponent
    ],
    standalone: true
})
export class NewTaskComponent {

    constructor(private _dialogRef: MatDialogRef<NewTaskComponent>, private _router: Router) {
    }

    taskCreated(task: TaskInstance) {
        this._dialogRef.close();
        this._router.navigate(['tasks', task.id]);
    }

    cancel() {
        this._dialogRef.close();
        this._router.navigate(['tasks', 'all']);
    }
}
