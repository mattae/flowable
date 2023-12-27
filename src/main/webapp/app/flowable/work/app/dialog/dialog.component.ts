import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { NgClass, NgIf } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { TranslocoModule } from '@ngneat/transloco';
import { MatFormioComponent } from '@mattae/angular-shared';

@Component({
    selector: 'create-dialog',
    templateUrl: './dialog.component.html',
    styles: [
        `
            .fuse-confirmation-dialog-panel {
                @apply md:w-128;

                .mat-mdc-dialog-container {
                    .mat-mdc-dialog-surface {
                        padding: 0 !important;
                    }
                }
            }
        `
    ],
    encapsulation: ViewEncapsulation.None,
    standalone: true,
    imports: [
        NgIf,
        MatButtonModule,
        MatDialogModule,
        MatIconModule,
        NgClass,
        TranslocoModule,
        MatFormioComponent
    ],
})
export class CreateDialogComponent {
    private submission: any;

    constructor(@Inject(MAT_DIALOG_DATA) public data: any, private dialogRef: MatDialogRef<CreateDialogComponent>,) {
    }

    submit() {
        this.dialogRef.close(this.submission);
    }

    onChange(event: any) {
        this.submission = event.data;
    }
}
