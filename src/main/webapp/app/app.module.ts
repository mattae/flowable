import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { APP_ROUTES } from './app.routes';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { StoreModule } from '@ngrx/store';
import { AppDefComponent } from './flowable/work/app.component';
import { MatButtonModule } from '@angular/material/button';
import { NgxEditorModule } from 'ngx-editor';
import { FormioModule } from '@formio/angular';

@NgModule({
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        RouterModule.forRoot(APP_ROUTES),
        NgxEditorModule,
        HttpClientModule,
        StoreModule.forRoot({}),
        AppDefComponent,
        MatButtonModule,
        NgxEditorModule,
        FormioModule,
    ],
    declarations: [
        HomeComponent,
        AppComponent,
    ],
    providers: [],
    bootstrap: [
        AppComponent
    ]
})
export class AppModule {
}
