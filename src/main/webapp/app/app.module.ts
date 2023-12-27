import { ApplicationConfig } from '@angular/core';
import {
    PreloadAllModules,
    provideRouter,
    withDebugTracing,
    withInMemoryScrolling,
    withPreloading
} from '@angular/router';
import { APP_ROUTES } from './app.routes';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { TranslocoHttpLoader } from '@mattae/angular-shared';
import { provideTransloco } from '@ngneat/transloco';
import { provideIcons } from './icons.provider';
import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import { provideFuse } from './fuse.provider';

export const appConfig: ApplicationConfig = {
    providers: [
        provideAnimations(),
        provideHttpClient(),
        provideRouter(APP_ROUTES,
            withDebugTracing(),
            withPreloading(PreloadAllModules),
            withInMemoryScrolling({scrollPositionRestoration: 'enabled'}),
        ),
        provideTransloco({
            config: {
                availableLangs: [
                    {
                        id: 'en',
                        label: 'English',
                    },
                    {
                        id: 'fr',
                        label: 'French'
                    }
                ],
                defaultLang: 'en',
                fallbackLang: 'en',
                reRenderOnLangChange: true,
                prodMode: true,
                missingHandler: {
                    useFallbackTranslation: true
                }
            },
            loader: TranslocoHttpLoader
        }),
        provideIcons(),
       /* {
            provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {
                appearance: 'outline'
            }
        },*/
        provideFuse({
            fuse: {
                layout: 'classy',
                scheme: 'light',
                screens: {
                    sm: '600px',
                    md: '960px',
                    lg: '1280px',
                    xl: '1440px',
                },
                theme: 'theme-default',
                themes: [
                    {
                        id: 'theme-default',
                        name: 'Default',
                    },
                    {
                        id: 'theme-brand',
                        name: 'Brand',
                    },
                    {
                        id: 'theme-teal',
                        name: 'Teal',
                    },
                    {
                        id: 'theme-rose',
                        name: 'Rose',
                    },
                    {
                        id: 'theme-purple',
                        name: 'Purple',
                    },
                    {
                        id: 'theme-amber',
                        name: 'Amber',
                    },
                ],
            },
        }),
    ]
};
