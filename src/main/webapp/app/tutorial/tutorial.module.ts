import { Component, Injectable, NgModule } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    Resolve,
    Router,
    RouterModule,
    RouterStateSnapshot,
    Routes,
    UrlTree
} from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { TutorialListComponent } from './components/list/tutorial-list.component';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import { MatDividerModule } from '@angular/material/divider';
import { TutorialService } from './tutorial.service';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSidenavModule } from '@angular/material/sidenav';
import { TranslocoModule } from '@ngneat/transloco';
import { TutorialDetailsComponent } from './components/details/tutorial.details.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { catchError, EMPTY, mergeMap, Observable, of } from 'rxjs';

@Injectable()
export class TutorialResolve implements Resolve<any> {
    constructor(private service: TutorialService, private router: Router) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {
        const id = route.params['id'] ? route.params['id'] : null;
        // @ts-ignore
        return this.service.getTutorial(id).pipe(
            catchError((err) => {
                this.router.navigateByUrl('/tutorials');
                return EMPTY;
            }),
            mergeMap((res: any) => {
                return of(res);
            })
        );
    }
}

@Injectable()
export class TutorialsResolve implements Resolve<any[]> {
    constructor(private service: TutorialService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any[]> | Promise<any[]> | any[] {
        return this.service.getTutorials();
    }
}

const canDeactivateTutorialDetails = (
    component: TutorialDetailsComponent,
    currentRoute: ActivatedRouteSnapshot,
    currentState: RouterStateSnapshot,
    nextState: RouterStateSnapshot
): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree => {
    // Get the next route
    let nextRoute: ActivatedRouteSnapshot = nextState.root;
    while (nextRoute.firstChild) {
        nextRoute = nextRoute.firstChild;
    }

    // If the next state doesn't contain '/plugins'
    // it means we are navigating away from the
    // plugin manager app
    if (!nextState.url.includes('/tutorials')) {
        // Let it navigate
        return true;
    }

    // If we are navigating to another plugin...
    if (nextState.url.includes('/details')) {
        // Just navigate
        return true;
    }
    // Otherwise...
    else {
        // Close the drawer first, and then navigate
        return component.closeDrawer().then(() => true);
    }
}

@Component({
    selector: 'tutorial-manager',
    template: '<router-outlet></router-outlet>'
})
export class TutorialManagerComponent {

}

const ROUTES: Routes = [
    {
        path: '',
        component: TutorialManagerComponent,
        children: [
            {
                path: '',
                component: TutorialListComponent,
                resolve: {
                    tutorials: TutorialsResolve
                },
                data: {
                    title: 'PLUGINS.TUTORIAL.TITLES.TUTORIALS'
                },
                children: [
                    {
                        path: 'details/:id',
                        component: TutorialDetailsComponent,
                        resolve: {
                            tutorial: TutorialResolve
                        },
                        data: {
                            authorities: ['ROLE_ADMIN'],
                            title: 'PLUGINS.TUTORIAL.TITLES.TUTORIAL_DETAILS'
                        },
                        canDeactivate: [canDeactivateTutorialDetails]
                    },
                    {
                        path: 'details',
                        component: TutorialDetailsComponent,
                        data: {
                            title: 'PLUGINS.TUTORIAL.TITLES.NEW_TUTORIAL'
                        },
                        canDeactivate: [canDeactivateTutorialDetails]
                    }
                ]
            }
        ]
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule.forChild(ROUTES),
        MatIconModule,
        MatToolbarModule,
        MatButtonModule,
        MatCardModule,
        MatInputModule,
        MatCheckboxModule,
        MatTableModule,
        MatDividerModule,
        MatPaginatorModule,
        MatSidenavModule,
        TranslocoModule,
        ReactiveFormsModule,
        MatTooltipModule,
    ],
    declarations: [
        TutorialListComponent,
        TutorialDetailsComponent,
        TutorialManagerComponent
    ],
    exports: [],
    providers: [
        TutorialResolve,
        TutorialsResolve,
        TutorialService,

    ]
})
export class TutorialModule {

}
