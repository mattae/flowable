import {
    ChangeDetectorRef,
    Component,
    ENVIRONMENT_INITIALIZER,
    forwardRef,
    NgZone,
    Optional,
    ViewChild,
    ViewContainerRef
} from '@angular/core';
import { FormioAppConfig, FormioBaseComponent } from '@formio/angular';
import { Form, initRenderer, registerComponent } from './renderer';
import { get } from 'lodash';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { NgForOf, NgIf } from '@angular/common';
import { MaterialSignatureComponent } from './components/signature/signature.component';
import { MaterialButtonComponent } from './components/button/button.component';
import { MaterialTextfieldComponent } from './components/textfield/textfield.component';
import { MaterialPasswordComponent } from './components/password/password.component';
import { MaterialUrlComponent } from './components/url/url.component';
import { MaterialEmailComponent } from './components/email/email.component';
import { MaterialPhoneNumberComponent } from './components/phonenumber/phonenumber.component';
import { MaterialNumberComponent } from './components/number/number.component';
import { MaterialCurrencyComponent } from './components/currency/currency.component';
import { MaterialDayComponent } from './components/day/day.component';
import { MaterialHiddenComponent } from './components/hidden/hidden.component';
import { MaterialHtmlComponent } from './components/html/html.component';
import { MaterialTagsComponent } from './components/tags/tags.component';
import { MaterialTableComponent } from './components/table/table.component';
import { MaterialTextareaComponent } from './components/textarea/textarea.component';
import { MaterialColumnsComponent } from './components/columns/columns.component';
import { MaterialContainerComponent } from './components/container/container.component';
import { MaterialDataGridComponent } from './components/datagrid/datagrid.component';
import { MaterialEditGridComponent } from './components/editgrid/editgrid.component';
import { MaterialPanelComponent } from './components/panel/panel.component';
import { MaterialCheckboxComponent } from './components/checkbox/checkbox.component';
import { MaterialFieldsetComponent } from './components/fieldset/fieldset.component';
import { MaterialContentComponent } from './components/content/content.component';
import { MaterialSurveyComponent } from './components/survey/survey.component';
import { MaterialSelectBoxesComponent } from './components/selectboxes/selectboxes.component';
import { MaterialRadioComponent } from './components/radio/radio.component';
import { MaterialSelectComponent } from './components/select/select.component';
import { MaterialTabsComponent } from './components/tabs/tabs.component';
import { MaterialDateComponent } from './components/date/date.component';
import { MaterialWellComponent } from './components/well/well.component';
import { MaterialComponent } from './components/MaterialComponent';
import { MaterialNestedComponent } from './components/MaterialNestedComponent';
import { MaterialTimeComponent } from './components/time/time.component';

@Component({
    selector: 'mat-formio',
    styles: [
        `.alert-danger {
            color: #721c24;
            background-color: #f8d7da;
            border-color: #f5c6cb;
        }

        .alert-success {
            color: #155724;
            background-color: #d4edda;
            border-color: #c3e6cb;
        }

        .alert {
            position: relative;
            padding: .75rem 1.25rem;
            margin-bottom: 0.5rem;
            border: 1px solid transparent;
            border-radius: .25rem;
        }

        ::ng-deep mat-card {
            box-sizing: border-box;
            -moz-box-sizing: border-box;
        }
        `
    ],
    template: `
        <mat-spinner *ngIf="isLoading"></mat-spinner>
        <div *ngIf="!this.options?.disableAlerts">
            <div *ngFor="let alert of alerts.alerts"
                 class="alert alert-{{ alert.type }}"
                 role="alert"
            >
                {{ alert.message }}
            </div>
        </div>
        <div class="flex flex-col gap-1 p-2">
            <ng-template #formio></ng-template>
        </div>
    `,
    imports: [
        MatProgressSpinnerModule,
        NgIf,
        NgForOf,
        forwardRef(() => MatFormioComponent),
        MaterialButtonComponent,
        MaterialTextfieldComponent,
        MaterialPasswordComponent,
        MaterialUrlComponent,
        MaterialEmailComponent,
        MaterialPhoneNumberComponent,
        MaterialNumberComponent,
        MaterialCurrencyComponent,
        MaterialDayComponent,
        MaterialHiddenComponent,
        MaterialHtmlComponent,
        MaterialTagsComponent,
        MaterialTableComponent,
        MaterialTextareaComponent,
        MaterialColumnsComponent,
        MaterialContainerComponent,
        MaterialDataGridComponent,
        MaterialEditGridComponent,
        MaterialPanelComponent,
        MaterialCheckboxComponent,
        MaterialFieldsetComponent,
        MaterialContentComponent,
        MaterialSignatureComponent,
        MaterialSurveyComponent,
        MaterialSelectBoxesComponent,
        MaterialRadioComponent,
        MaterialSelectComponent,
        MaterialTabsComponent,
        MaterialDateComponent,
        MaterialWellComponent,
        MaterialComponent,
        MaterialNestedComponent,
        MaterialTimeComponent
    ],
    providers: [
    ],
    standalone: true,
})
export class MatFormioComponent extends FormioBaseComponent {
    @ViewChild('formio', {static: true, read: ViewContainerRef}) formioViewContainer: ViewContainerRef | undefined;

    constructor(
        private cd: ChangeDetectorRef,
        public override ngZone: NgZone,
        @Optional() public override config: FormioAppConfig
    ) {
        super(ngZone, config);
        initRenderer();
        registerComponent('signature', MaterialSignatureComponent);
    }

    override getRendererOptions(): any {
        const rendererOptions = super.getRendererOptions();
        return {...rendererOptions, validateOnInit: get(rendererOptions, 'validateOnInit', true)};
    }


    override createRenderer() {
        const options = this.getRendererOptions();
        const flags = {
            validateOnInit: options.validateOnInit
        };
        const form = new Form();
        form._form = this.form;
        form.options = options;
        form.options.events = form.events;
        form.instance = form.create(this.form!.display);
        form.instance.viewContainer = () => this.formioViewContainer;
        if (this.submission && this.submission.data) {
            form.instance.data = this.submission.data;
        }
        this.ngZone.run(() => {
                form.instance.setForm(this.form, flags)
                    .then(() => form.readyResolve(form.instance))
                    .catch(() => form.readyReject());
            }
        );
        return form.instance;
    }
}
export {
    MaterialButtonComponent,
    MaterialTextfieldComponent,
    MaterialPasswordComponent,
    MaterialUrlComponent,
    MaterialEmailComponent,
    MaterialPhoneNumberComponent,
    MaterialNumberComponent,
    MaterialCurrencyComponent,
    MaterialDayComponent,
    MaterialHiddenComponent,
    MaterialHtmlComponent,
    MaterialTagsComponent,
    MaterialTableComponent,
    MaterialTextareaComponent,
    MaterialColumnsComponent,
    MaterialContainerComponent,
    MaterialDataGridComponent,
    MaterialEditGridComponent,
    MaterialPanelComponent,
    MaterialCheckboxComponent,
    MaterialFieldsetComponent,
    MaterialContentComponent,
    MaterialSignatureComponent,
    MaterialSurveyComponent,
    MaterialSelectBoxesComponent,
    MaterialRadioComponent,
    MaterialSelectComponent,
    MaterialTabsComponent,
    MaterialDateComponent,
    MaterialWellComponent,
    MaterialComponent,
    MaterialNestedComponent,
    MaterialTimeComponent
};
export * from './renderer';
