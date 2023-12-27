import { appConfig } from './app/app.module';
import { environment } from './environments/environment';
import { enableProdMode } from '@angular/core';
import { AppComponent } from './app/app.component';
import { bootstrapApplication } from '@angular/platform-browser';

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, appConfig)
    .catch(err => console.error(err));
