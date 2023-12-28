import { Component, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { Subject } from 'rxjs';
import { AppsComponent } from './app/apps.component';

@Component({
    selector: 'flw-create-work',
    template: ``,
    standalone: true
})
export class CreateWorkComponent implements OnDestroy {
    destroy = new Subject<any>();

    constructor(
        private _dialog: MatDialog,
        private router: Router
    ) {
        this._dialog.open(AppsComponent, {
            width: '50%'
        });
    }

    ngOnDestroy() {
        this.destroy.next(null);
        this.destroy.complete();
    }
}
