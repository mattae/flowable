import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TutorialService } from '../../tutorial.service';
import { Subject, takeUntil } from 'rxjs';
import { MatDrawer } from '@angular/material/sidenav';

@Component({
    selector: 'tutorial-list',
    templateUrl: './tutorial-list.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class TutorialListComponent implements OnInit, OnDestroy {

    @ViewChild('matDrawer', {static: true}) matDrawer: MatDrawer;
    tutorials: any[] = [];
    keyword = '';
    pageSize = 10;
    drawerMode: 'side' | 'over';

    private _unsubscribeAll: Subject<any> = new Subject<any>();

    constructor(private tutorialService: TutorialService,
                private _activatedRoute: ActivatedRoute,
                private _changeDetectorRef: ChangeDetectorRef,
                private _router: Router,) {
    }

    ngOnInit(): void {
        this._activatedRoute.data.subscribe(({tutorials}) => {
            this.tutorials = tutorials;
            this._changeDetectorRef.markForCheck();
        });

        /*this._mediaWatcherService.onMediaQueryChange$('(min-width: 1440px)')
            .pipe(takeUntil(this._unsubscribeAll))
            .subscribe((state) => {
                // Calculate the drawer mode
                this.drawerMode = state.matches ? 'side' : 'over';

                // Mark for check
                this._changeDetectorRef.markForCheck();
            });*/

        this.matDrawer.openedChange.subscribe(opened => {
            if (!opened) {
                this.searchTutorial('');
            }
        })
    }

    ngOnDestroy(): void {
        // Unsubscribe from all subscriptions
        this._unsubscribeAll.next(null);
        this._unsubscribeAll.complete();
    }

    filterByQuery(query: string): any {
        this.searchTutorial(query)
        this._changeDetectorRef.markForCheck();
    }

    onBackdropClicked(): void {
        // Go back to the list
        this._router.navigate(['./'], {relativeTo: this._activatedRoute});

        // Mark for check
        this._changeDetectorRef.markForCheck();
    }


    searchTutorial(title: any): void {
        this.tutorialService.getTutorialsByTitle(title).subscribe(res => this.tutorials = res);
    }

    deleteAll(): void {
        this.tutorialService.deleteAllTutorials().subscribe(res => this.tutorials = []);
    }

    trackByFn(index: number, item: any): any {
        return item.id || index;
    }
}
