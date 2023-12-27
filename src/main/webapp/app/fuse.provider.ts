import { ENVIRONMENT_INITIALIZER, EnvironmentProviders, importProvidersFrom, inject, Provider } from '@angular/core';
import { MATERIAL_SANITY_CHECKS } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import {
    FUSE_CONFIG,
    FuseConfig,
    FuseConfirmationService,
    FuseMediaWatcherService,
    FuseUtilsService
} from '@mattae/angular-shared';

export type FuseProviderConfig = {
    fuse?: FuseConfig
}

/**
 * Fuse provider
 */
export const provideFuse = (config: FuseProviderConfig): Array<Provider | EnvironmentProviders> => {
    // Base providers
    // Return the providers
    return [
        {
            // Disable 'theme' sanity check
            provide: MATERIAL_SANITY_CHECKS,
            useValue: {
                doctype: true,
                theme: false,
                version: true,
            },
        },
        {
            // Use the 'fill' appearance on Angular Material form fields by default
            provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
            useValue: {
                appearance: 'fill',
            },
        },
        importProvidersFrom(MatDialogModule),
        {
            provide: ENVIRONMENT_INITIALIZER,
            useValue: () => inject(FuseConfirmationService),
            multi: true,
        },
        {
            provide: FUSE_CONFIG,
            useValue: config?.fuse ?? {},
        },

        {
            provide: ENVIRONMENT_INITIALIZER,
            useValue: () => inject(FuseMediaWatcherService),
            multi: true,
        },
        {
            provide: ENVIRONMENT_INITIALIZER,
            useValue: () => inject(FuseUtilsService),
            multi: true,
        },
    ];
};
