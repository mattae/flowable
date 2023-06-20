import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormGroup, UntypedFormBuilder, Validators } from '@angular/forms';
import { TutorialService } from '../../tutorial.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDrawerToggleResult } from '@angular/material/sidenav';
import { TutorialListComponent } from '../list/tutorial-list.component';

@Component({
    selector: 'tutorial-details',
    templateUrl: './tutorial.detail.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class TutorialDetailsComponent implements OnInit {
    tutorial: any;
    pathHasId = false;

    editMode: boolean = false;
    formGroup: FormGroup;

    constructor(private tutorialService: TutorialService,
                private _activatedRoute: ActivatedRoute,
                private _changeDetectorRef: ChangeDetectorRef,
                private fb: UntypedFormBuilder,
                private _router: Router,
                private tutorialListComponent: TutorialListComponent) {

        this.formGroup = fb.group({
            id: [],
            title: ['', Validators.required],
            description: ['', [Validators.required]],
            published: false
        });
    }

    ngOnInit(): void {
        this.tutorialListComponent.matDrawer.open();
        this.pathHasId = !!this._activatedRoute.snapshot.params['id'];
        this._activatedRoute.data.subscribe(({tutorial}) => {
            if (tutorial) {
                this.tutorial = tutorial;
                this.formGroup.patchValue(tutorial);
            } else {
                this.editMode = true;
                this.tutorial = {}
            }

            this._changeDetectorRef.markForCheck();
        });
    }

    closeDrawer(): Promise<MatDrawerToggleResult> {
        return this.tutorialListComponent.matDrawer.close();
    }

    toggleEditMode(editMode: boolean | null = null): void {
        if (editMode === null) {
            this.editMode = !this.editMode;
        } else {
            this.editMode = editMode;
        }

        this._changeDetectorRef.markForCheck();
    }

    save(): void {
        this.tutorial = this.formGroup.value;
        this.tutorialService.saveTutorial(this.tutorial).subscribe(
            res => {
                this.tutorial = res;
                this.editMode = false;
                this._changeDetectorRef.markForCheck();
            },
            error => {

            }
        );
    }

    delete() {
        this.tutorialService.deleteTutorial(this.tutorial.id).subscribe(res => {
            this._router.navigate(['../..'])
        })
    }
}
