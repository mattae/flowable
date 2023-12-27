import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    OnDestroy,
    OnInit,
    ViewEncapsulation
} from '@angular/core';
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import {
    AccountService,
    ConfigurationService,
    FuseFullscreenComponent,
    FuseMediaWatcherService,
    FuseNavigationItem,
    FuseNavigationService,
    FuseScrollbarDirective,
    FuseScrollResetDirective,
    FuseVerticalNavigationComponent,
    User
} from '@mattae/angular-shared';
import { HttpClient } from '@angular/common/http';
import { NotificationsComponent } from '../../../common/notifications/notifications.component';
import { UserComponent } from '../../../common/user/user.component';
import { AsyncPipe } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { LanguagesComponent } from '../../../common/languages/languages.component';
import { MatSidenavModule } from '@angular/material/sidenav';

@Component({
    selector: 'classy-layout',
    templateUrl: './classy.component.html',
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush,
    imports: [
        FuseVerticalNavigationComponent,
        NotificationsComponent,
        UserComponent,
        MatIconModule,
        MatButtonModule,
        FuseFullscreenComponent,
        RouterOutlet,
        LanguagesComponent,
        AsyncPipe,
        FuseScrollbarDirective,
        MatSidenavModule,
        FuseScrollResetDirective
    ],
    standalone: true
})
export class ClassyLayoutComponent implements OnInit, OnDestroy {
    isScreenSmall: boolean;
    user: User;
    logoUrl: string
    serverName: string;
    private _unsubscribeAll: Subject<any> = new Subject<any>();
    defaultNavigation: FuseNavigationItem[] = [
        {
            id: 'example',
            type: 'basic',
            link: '/'
        }
    ];

    /**
     * Constructor
     */
    constructor(
        private _activatedRoute: ActivatedRoute,
        private _router: Router,
        private _changeDetectorRef: ChangeDetectorRef,
        private _userService: AccountService,
        private _fuseMediaWatcherService: FuseMediaWatcherService,
        private _fuseNavigationService: FuseNavigationService,
        private _http: HttpClient,
        private _configurationService: ConfigurationService,
    ) {
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Accessors
    // -----------------------------------------------------------------------------------------------------

    /**
     * Getter for current year
     */
    get currentYear(): number {
        return new Date().getFullYear();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Lifecycle hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On init
     */
    async ngOnInit() {


        // Subscribe to media changes
        this._fuseMediaWatcherService.onMediaChange$
            .pipe(takeUntil(this._unsubscribeAll))
            .subscribe(({matchingAliases}) => {

                // Check if the screen is small
                this.isScreenSmall = !matchingAliases.includes('md');
                this._changeDetectorRef.markForCheck();
            });
    }

    /**
     * On destroy
     */
    ngOnDestroy(): void {
        // Unsubscribe from all subscriptions
        this._unsubscribeAll.next(null);
        this._unsubscribeAll.complete();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Toggle navigation
     *
     * @param name
     */
    toggleNavigation(name: string): void {
        // Get the navigation
        const navigation = this._fuseNavigationService.getComponent<FuseVerticalNavigationComponent>(name);

        if (navigation) {
            // Toggle the opened status
            navigation.toggle();
            this._changeDetectorRef.markForCheck();
        }
    }
}
