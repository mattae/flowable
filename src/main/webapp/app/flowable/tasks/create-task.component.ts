import { Component, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NewTaskComponent } from './new-task.component';

@Component({
    selector: 'flw-create-task',
    template: ``,
    standalone: true
})
export class CreateTaskComponent implements OnDestroy {
    destroy = new Subject<any>();

    constructor(
        private _dialog: MatDialog,
        private router: Router
    ) {
        this._dialog.open(NewTaskComponent, {
            width: '50%'
        });
    }

    ngOnDestroy() {
        this.destroy.next(null);
        this.destroy.complete();
    }
}
