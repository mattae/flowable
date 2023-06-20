import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { MatFormioModule } from '../../formio/angular-material-formio.module';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';
import { AngularSplitModule } from 'angular-split';
import { WorkService } from '../services/work.service';
import { AppsComponent } from './app/apps.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { AdhocTaskComponent } from '../task/adhoc-task.component';

@Component({
    selector: 'flowable-work',
    templateUrl: 'work.component.html',
    standalone: true,
    imports: [
        CommonModule,
        //MatFormioModule,
        RouterOutlet,
        RouterLink,
        AngularSplitModule,
        AppsComponent,
        MatDatepickerModule,
        FormsModule,
        MatSelectModule,
        MatAutocompleteModule,
        ReactiveFormsModule,
        AdhocTaskComponent,
        MatFormioModule
    ],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class WorkComponent implements OnInit, OnDestroy {

    ngOnInit() {

    }

    constructor(private _changeDetectorRef: ChangeDetectorRef, private ws: WorkService) {
    }
    ngOnDestroy(): void {
    }

    form = {
        "_id": "5b8c14e57f43cc40ba58e2bf",
        "type": "form",
        "components": [
            {
                "hideLabel": false,
                "clearOnHide": false,
                "conditional": {
                    "eq": "",
                    "when": null,
                    "show": ""
                },
                "theme": "default",
                "key": "page1",
                "input": false,
                "components": [
                    {
                        "hideLabel": false,
                        "type": "textfield",
                        "conditional": {
                            "eq": "",
                            "when": null,
                            "show": ""
                        },
                        "validate": {
                            "customPrivate": false,
                            "custom": "",
                            "pattern": "",
                            "maxLength": "",
                            "minLength": "",
                            "required": false
                        },
                        "persistent": true,
                        "unique": false,
                        "protected": false,
                        "defaultValue": "",
                        "multiple": true,
                        "suffix": "",
                        "prefix": "",
                        "placeholder": "",
                        "key": "textfieldonpage1",
                        "label": "Textfield on page 1",
                        "inputMask": "",
                        "inputType": "text",
                        "tableView": true,
                        "input": true,
                        "hidden": false,
                        "clearOnHide": true,
                        "autofocus": false,
                        "spellcheck": true
                    },
                    {
                        "hideLabel": false,
                        "conditional": {
                            "eq": "",
                            "when": null,
                            "show": ""
                        },
                        "type": "number",
                        "validate": {
                            "custom": "",
                            "multiple": "",
                            "integer": "",
                            "step": "any",
                            "max": "",
                            "min": "",
                            "required": false
                        },
                        "persistent": true,
                        "protected": false,
                        "defaultValue": 0,
                        "suffix": "",
                        "prefix": "",
                        "placeholder": "",
                        "key": "numberField",
                        "label": "Number Field",
                        "inputType": "number",
                        "tableView": true,
                        "input": true,
                        "hidden": false,
                        "clearOnHide": true,
                        "autofocus": false
                    }
                ],
                "title": "First",
                "type": "panel",
                "tableView": false
            },
            {
                "hideLabel": false,
                "tableView": false,
                "clearOnHide": false,
                "theme": "default",
                "key": "page2",
                "input": false,
                "components": [
                    {
                        "hideLabel": false,
                        "clearOnHide": true,
                        "hidden": false,
                        "type": "textfield",
                        "conditional": {
                            "eq": "",
                            "when": null,
                            "show": ""
                        },
                        "validate": {
                            "customPrivate": false,
                            "custom": "",
                            "pattern": "",
                            "maxLength": "",
                            "minLength": "",
                            "required": false
                        },
                        "persistent": true,
                        "unique": false,
                        "protected": false,
                        "defaultValue": "",
                        "multiple": false,
                        "suffix": "",
                        "prefix": "",
                        "placeholder": "",
                        "key": "textfieldonPage2",
                        "label": "Textfield on Page 2",
                        "inputMask": "",
                        "inputType": "text",
                        "tableView": true,
                        "input": true,
                        "autofocus": false,
                        "spellcheck": true
                    },
                    {
                        "input": true,
                        "tableView": true,
                        "label": "Customer",
                        "key": "page2Customer",
                        "placeholder": "Select a customer",
                        "data": {
                            "values": [
                                {
                                    "value": "",
                                    "label": ""
                                }
                            ],
                            "json": "",
                            "url": "https://examples.form.io/customer/submission",
                            "resource": "",
                            "custom": "",
                            "headers": [
                                {
                                    "value": "",
                                    "key": ""
                                }
                            ]
                        },
                        "dataSrc": "url",
                        "valueProperty": "data.email",
                        "defaultValue": "",
                        "refreshOn": "",
                        "filter": "",
                        "authenticate": false,
                        "template": "<span>{{ item.data.firstName }} {{ item.data.lastName }}</span>",
                        "multiple": false,
                        "protected": false,
                        "unique": false,
                        "persistent": true,
                        "hidden": false,
                        "clearOnHide": true,
                        "validate": {
                            "required": false
                        },
                        "type": "select",
                        "lazyLoad": true,
                        "widget": "html5",
                        "hideLabel": false,
                        "labelPosition": "top",
                        "tags": [],
                        "conditional": {
                            "show": "",
                            "when": null,
                            "eq": ""
                        },
                        "properties": {},
                        "searchField": "data.email",
                        "autofocus": false
                    },
                    {
                        "hideLabel": false,
                        "clearOnHide": false,
                        "conditional": {
                            "eq": "",
                            "when": null,
                            "show": ""
                        },
                        "type": "fieldset",
                        "components": [
                            {
                                "hideLabel": false,
                                "clearOnHide": true,
                                "hidden": false,
                                "type": "textfield",
                                "conditional": {
                                    "eq": "",
                                    "when": null,
                                    "show": ""
                                },
                                "validate": {
                                    "customPrivate": false,
                                    "custom": "",
                                    "pattern": "",
                                    "maxLength": "",
                                    "minLength": "",
                                    "required": false
                                },
                                "persistent": true,
                                "unique": false,
                                "protected": false,
                                "defaultValue": "",
                                "multiple": false,
                                "suffix": "",
                                "prefix": "",
                                "placeholder": "",
                                "key": "textfield",
                                "label": "Textfield",
                                "inputMask": "",
                                "inputType": "text",
                                "tableView": true,
                                "input": true,
                                "autofocus": false,
                                "spellcheck": true
                            }
                        ],
                        "legend": "FieldSet Label",
                        "tableView": true,
                        "input": false
                    }
                ],
                "title": "Page 2",
                "type": "panel"
            },
            {
                "properties": {
                    "": ""
                },
                "conditional": {
                    "eq": "",
                    "when": null,
                    "show": ""
                },
                "tags": [],
                "hideLabel": false,
                "breadcrumb": "default",
                "type": "panel",
                "components": [
                    {
                        "properties": {
                            "": ""
                        },
                        "conditional": {
                            "eq": "",
                            "when": null,
                            "show": ""
                        },
                        "tags": [],
                        "hideLabel": false,
                        "type": "datagrid",
                        "clearOnHide": true,
                        "hidden": false,
                        "persistent": true,
                        "protected": false,
                        "key": "panelDataGrid",
                        "label": "Data Grid",
                        "tableView": true,
                        "components": [
                            {
                                "properties": {
                                    "": ""
                                },
                                "tags": [],
                                "labelPosition": "top",
                                "hideLabel": true,
                                "type": "textfield",
                                "conditional": {
                                    "eq": "",
                                    "when": null,
                                    "show": ""
                                },
                                "validate": {
                                    "customPrivate": false,
                                    "custom": "",
                                    "pattern": "",
                                    "maxLength": "",
                                    "minLength": "",
                                    "required": false
                                },
                                "clearOnHide": true,
                                "hidden": false,
                                "persistent": true,
                                "unique": false,
                                "protected": false,
                                "defaultValue": "",
                                "multiple": false,
                                "suffix": "",
                                "prefix": "",
                                "placeholder": "",
                                "key": "panelDataGridA",
                                "label": "A",
                                "inputMask": "",
                                "inputType": "text",
                                "tableView": true,
                                "input": true,
                                "autofocus": false,
                                "spellcheck": true,
                                "inDataGrid": true
                            },
                            {
                                "properties": {
                                    "": ""
                                },
                                "tags": [],
                                "labelPosition": "top",
                                "hideLabel": true,
                                "type": "textfield",
                                "conditional": {
                                    "eq": "",
                                    "when": null,
                                    "show": ""
                                },
                                "validate": {
                                    "customPrivate": false,
                                    "custom": "",
                                    "pattern": "",
                                    "maxLength": "",
                                    "minLength": "",
                                    "required": false
                                },
                                "clearOnHide": true,
                                "hidden": false,
                                "persistent": true,
                                "unique": false,
                                "protected": false,
                                "defaultValue": "",
                                "multiple": false,
                                "suffix": "",
                                "prefix": "",
                                "placeholder": "",
                                "key": "panelDataGridB",
                                "label": "B",
                                "inputMask": "",
                                "inputType": "text",
                                "tableView": true,
                                "input": true,
                                "autofocus": false,
                                "spellcheck": true,
                                "inDataGrid": true
                            },
                            {
                                "properties": {
                                    "": ""
                                },
                                "tags": [],
                                "labelPosition": "top",
                                "hideLabel": true,
                                "type": "textfield",
                                "conditional": {
                                    "eq": "",
                                    "when": null,
                                    "show": ""
                                },
                                "validate": {
                                    "customPrivate": false,
                                    "custom": "",
                                    "pattern": "",
                                    "maxLength": "",
                                    "minLength": "",
                                    "required": false
                                },
                                "clearOnHide": true,
                                "hidden": false,
                                "persistent": true,
                                "unique": false,
                                "protected": false,
                                "defaultValue": "",
                                "multiple": false,
                                "suffix": "",
                                "prefix": "",
                                "placeholder": "",
                                "key": "panelDataGridC",
                                "label": "C",
                                "inputMask": "",
                                "inputType": "text",
                                "tableView": true,
                                "input": true,
                                "autofocus": false,
                                "spellcheck": true,
                                "inDataGrid": true
                            },
                            {
                                "properties": {
                                    "": ""
                                },
                                "tags": [],
                                "labelPosition": "top",
                                "hideLabel": true,
                                "type": "textfield",
                                "conditional": {
                                    "eq": "",
                                    "when": null,
                                    "show": ""
                                },
                                "validate": {
                                    "customPrivate": false,
                                    "custom": "",
                                    "pattern": "",
                                    "maxLength": "",
                                    "minLength": "",
                                    "required": false
                                },
                                "clearOnHide": true,
                                "hidden": false,
                                "persistent": true,
                                "unique": false,
                                "protected": false,
                                "defaultValue": "",
                                "multiple": false,
                                "suffix": "",
                                "prefix": "",
                                "placeholder": "",
                                "key": "panelDataGridD",
                                "label": "D",
                                "inputMask": "",
                                "inputType": "text",
                                "tableView": true,
                                "input": true,
                                "autofocus": false,
                                "spellcheck": true,
                                "inDataGrid": true
                            }
                        ],
                        "tree": true,
                        "input": true,
                        "autofocus": false
                    },
                    {
                        "autofocus": false,
                        "input": true,
                        "tableView": true,
                        "label": "HTML5 Select",
                        "key": "panelHtml5Select",
                        "placeholder": "",
                        "data": {
                            "values": [
                                {
                                    "value": "orange",
                                    "label": "Orange"
                                },
                                {
                                    "value": "apple",
                                    "label": "Apple"
                                },
                                {
                                    "value": "banana",
                                    "label": "Banana"
                                },
                                {
                                    "value": "strawberry",
                                    "label": "Strawberry"
                                },
                                {
                                    "value": "kiwi",
                                    "label": "Kiwi"
                                }
                            ],
                            "json": "",
                            "url": "",
                            "resource": "",
                            "custom": ""
                        },
                        "widget": "html5",
                        "dataSrc": "values",
                        "valueProperty": "",
                        "defaultValue": "",
                        "refreshOn": "",
                        "filter": "",
                        "authenticate": false,
                        "template": "<span>{{ item.label }}</span>",
                        "multiple": false,
                        "protected": false,
                        "unique": false,
                        "persistent": true,
                        "hidden": false,
                        "clearOnHide": true,
                        "validate": {
                            "required": false
                        },
                        "type": "select",
                        "labelPosition": "top",
                        "tags": [],
                        "conditional": {
                            "show": "",
                            "when": null,
                            "eq": ""
                        },
                        "properties": {}
                    }
                ],
                "tableView": false,
                "theme": "default",
                "title": "Page 3",
                "input": false,
                "key": "panel",
                "clearOnHide": false
            },
            {
                "hideLabel": false,
                "clearOnHide": false,
                "conditional": {
                    "eq": "",
                    "when": null,
                    "show": ""
                },
                "theme": "default",
                "key": "page3",
                "input": false,
                "components": [
                    {
                        "hideLabel": false,
                        "type": "textfield",
                        "conditional": {
                            "eq": "",
                            "when": null,
                            "show": ""
                        },
                        "validate": {
                            "customPrivate": false,
                            "custom": "",
                            "pattern": "",
                            "maxLength": "",
                            "minLength": "",
                            "required": false
                        },
                        "persistent": true,
                        "unique": false,
                        "protected": false,
                        "defaultValue": "",
                        "multiple": false,
                        "suffix": "",
                        "prefix": "",
                        "placeholder": "",
                        "key": "textfieldonPage3",
                        "label": "Textfield on Page 3",
                        "inputMask": "",
                        "inputType": "text",
                        "tableView": true,
                        "input": true,
                        "hidden": false,
                        "clearOnHide": true,
                        "autofocus": false,
                        "spellcheck": true
                    },
                    {
                        "input": true,
                        "tableView": true,
                        "label": "Signature",
                        "key": "signature",
                        "placeholder": "",
                        "footer": "Sign above",
                        "width": "100%",
                        "height": "150px",
                        "penColor": "black",
                        "backgroundColor": "rgb(245,245,235)",
                        "minWidth": "0.5",
                        "maxWidth": "2.5",
                        "protected": false,
                        "persistent": true,
                        "hidden": false,
                        "clearOnHide": true,
                        "validate": {
                            "required": false
                        },
                        "type": "signature",
                        "hideLabel": true,
                        "tags": [],
                        "conditional": {
                            "show": "",
                            "when": null,
                            "eq": ""
                        },
                        "properties": {
                            "": ""
                        },
                        "lockKey": true
                    }
                ],
                "title": "Last",
                "type": "panel",
                "tableView": false
            },
            {
                "hideLabel": false,
                "type": "button",
                "theme": "primary",
                "disableOnInvalid": true,
                "action": "submit",
                "block": false,
                "rightIcon": "",
                "leftIcon": "",
                "size": "md",
                "key": "submit",
                "tableView": false,
                "label": "Submit",
                "input": true,
                "autofocus": false
            }
        ],
        "title": "Wizard",
        "name": "wizard",
    }

    form2={
        "_id": "57aa1d2a5b7a477b002717fe",
        "machineName": "examples:example",
        "modified": "2022-12-09T17:14:46.932Z",
        "title": "Example",
        "display": "form",
        "type": "form",
        "name": "example",
        "path": "example",
        "project": "5692b91fd1028f01000407e3",
        "created": "2016-08-09T18:12:58.126Z",
        "components": [
            {
                "input": false,
                "html": "<h1><b><a href=\"https://form.io\">Form.io</a></b> Example Form</h1>\n\n<p>This is a dynamically rendered JSON form&nbsp;built with <a href=\"https://form.io\">Form.io</a>. Using a simple&nbsp;drag-and-drop form builder, you can create any form that includes e-signatures, wysiwyg editors, date fields, layout components, data grids, surveys, etc.</p>\n",
                "type": "content",
                "conditional": {
                    "show": "",
                    "when": null,
                    "eq": ""
                },
                "key": "content",
                "placeholder": "",
                "prefix": "",
                "customClass": "",
                "suffix": "",
                "multiple": false,
                "defaultValue": null,
                "protected": false,
                "unique": false,
                "persistent": true,
                "hidden": false,
                "clearOnHide": true,
                "refreshOn": "",
                "redrawOn": "",
                "tableView": false,
                "modalEdit": false,
                "label": "Content",
                "dataGridLabel": false,
                "labelPosition": "top",
                "description": "",
                "errorLabel": "",
                "tooltip": "",
                "hideLabel": false,
                "tabindex": "",
                "disabled": false,
                "autofocus": false,
                "dbIndex": false,
                "customDefaultValue": "",
                "calculateValue": "",
                "calculateServer": false,
                "widget": null,
                "attributes": {},
                "validateOn": "change",
                "validate": {
                    "required": false,
                    "custom": "",
                    "customPrivate": false,
                    "strictDateValidation": false,
                    "multiple": false,
                    "unique": false
                },
                "overlay": {
                    "style": "",
                    "left": "",
                    "top": "",
                    "width": "",
                    "height": ""
                },
                "allowCalculateOverride": false,
                "encrypted": false,
                "showCharCount": false,
                "showWordCount": false,
                "properties": {},
                "allowMultipleMasks": false,
                "id": "e4w5wnn",
                "addons": []
            },
            {
                "input": false,
                "columns": [
                    {
                        "components": [
                            {
                                "tabindex": "1",
                                "tags": [],
                                "clearOnHide": true,
                                "hidden": false,
                                "input": true,
                                "tableView": true,
                                "inputType": "text",
                                "inputMask": "",
                                "label": "First Name",
                                "key": "firstName",
                                "placeholder": "Enter your first name",
                                "prefix": "",
                                "suffix": "",
                                "multiple": false,
                                "defaultValue": "",
                                "protected": false,
                                "unique": false,
                                "persistent": true,
                                "validate": {
                                    "required": false,
                                    "minLength": "",
                                    "maxLength": "",
                                    "pattern": "",
                                    "custom": "",
                                    "customPrivate": false,
                                    "strictDateValidation": false,
                                    "multiple": false,
                                    "unique": false
                                },
                                "conditional": {
                                    "show": "",
                                    "when": null,
                                    "eq": ""
                                },
                                "type": "textfield",
                                "customClass": "",
                                "refreshOn": "",
                                "redrawOn": "",
                                "modalEdit": false,
                                "dataGridLabel": false,
                                "labelPosition": "top",
                                "description": "",
                                "errorLabel": "",
                                "tooltip": "",
                                "hideLabel": false,
                                "disabled": false,
                                "autofocus": false,
                                "dbIndex": false,
                                "customDefaultValue": "",
                                "calculateValue": "",
                                "calculateServer": false,
                                "widget": {
                                    "type": "input"
                                },
                                "attributes": {},
                                "validateOn": "change",
                                "overlay": {
                                    "style": "",
                                    "left": "",
                                    "top": "",
                                    "width": "",
                                    "height": ""
                                },
                                "allowCalculateOverride": false,
                                "encrypted": false,
                                "showCharCount": false,
                                "showWordCount": false,
                                "properties": {},
                                "allowMultipleMasks": false,
                                "mask": false,
                                "inputFormat": "plain",
                                "spellcheck": true,
                                "id": "ek1pl3",
                                "addons": [],
                                "displayMask": "",
                                "truncateMultipleSpaces": false
                            },
                            {
                                "tabindex": "3",
                                "tags": [],
                                "clearOnHide": true,
                                "hidden": false,
                                "input": true,
                                "tableView": true,
                                "inputType": "email",
                                "label": "Email",
                                "key": "email",
                                "placeholder": "Enter your email address",
                                "prefix": "",
                                "suffix": "",
                                "defaultValue": "",
                                "protected": false,
                                "unique": false,
                                "persistent": true,
                                "type": "email",
                                "conditional": {
                                    "show": "",
                                    "when": null,
                                    "eq": ""
                                },
                                "kickbox": {
                                    "enabled": false
                                },
                                "customClass": "",
                                "multiple": false,
                                "refreshOn": "",
                                "redrawOn": "",
                                "modalEdit": false,
                                "dataGridLabel": false,
                                "labelPosition": "top",
                                "description": "",
                                "errorLabel": "",
                                "tooltip": "",
                                "hideLabel": false,
                                "disabled": false,
                                "autofocus": false,
                                "dbIndex": false,
                                "customDefaultValue": "",
                                "calculateValue": "",
                                "calculateServer": false,
                                "widget": {
                                    "type": "input"
                                },
                                "attributes": {},
                                "validateOn": "change",
                                "validate": {
                                    "required": false,
                                    "custom": "",
                                    "customPrivate": false,
                                    "strictDateValidation": false,
                                    "multiple": false,
                                    "unique": false,
                                    "minLength": "",
                                    "maxLength": "",
                                    "pattern": ""
                                },
                                "overlay": {
                                    "style": "",
                                    "left": "",
                                    "top": "",
                                    "width": "",
                                    "height": ""
                                },
                                "allowCalculateOverride": false,
                                "encrypted": false,
                                "showCharCount": false,
                                "showWordCount": false,
                                "properties": {},
                                "allowMultipleMasks": false,
                                "mask": false,
                                "inputFormat": "plain",
                                "inputMask": "",
                                "spellcheck": true,
                                "id": "en2328",
                                "addons": [],
                                "displayMask": "",
                                "truncateMultipleSpaces": false
                            }
                        ],
                        "width": 6,
                        "offset": 0,
                        "push": 0,
                        "pull": 0,
                        "size": "md",
                        "currentWidth": 6
                    },
                    {
                        "components": [
                            {
                                "tabindex": "2",
                                "tags": [],
                                "clearOnHide": true,
                                "hidden": false,
                                "input": true,
                                "tableView": true,
                                "inputType": "text",
                                "inputMask": "",
                                "label": "Last Name",
                                "key": "lastName",
                                "placeholder": "Enter your last name",
                                "prefix": "",
                                "suffix": "",
                                "multiple": false,
                                "defaultValue": "",
                                "protected": false,
                                "unique": false,
                                "persistent": true,
                                "validate": {
                                    "required": false,
                                    "minLength": "",
                                    "maxLength": "",
                                    "pattern": "",
                                    "custom": "",
                                    "customPrivate": false,
                                    "strictDateValidation": false,
                                    "multiple": false,
                                    "unique": false
                                },
                                "conditional": {
                                    "show": "",
                                    "when": null,
                                    "eq": ""
                                },
                                "type": "textfield",
                                "customClass": "",
                                "refreshOn": "",
                                "redrawOn": "",
                                "modalEdit": false,
                                "dataGridLabel": false,
                                "labelPosition": "top",
                                "description": "",
                                "errorLabel": "",
                                "tooltip": "",
                                "hideLabel": false,
                                "disabled": false,
                                "autofocus": false,
                                "dbIndex": false,
                                "customDefaultValue": "",
                                "calculateValue": "",
                                "calculateServer": false,
                                "widget": {
                                    "type": "input"
                                },
                                "attributes": {},
                                "validateOn": "change",
                                "overlay": {
                                    "style": "",
                                    "left": "",
                                    "top": "",
                                    "width": "",
                                    "height": ""
                                },
                                "allowCalculateOverride": false,
                                "encrypted": false,
                                "showCharCount": false,
                                "showWordCount": false,
                                "properties": {},
                                "allowMultipleMasks": false,
                                "mask": false,
                                "inputFormat": "plain",
                                "spellcheck": true,
                                "id": "ex6xzd",
                                "addons": [],
                                "displayMask": "",
                                "truncateMultipleSpaces": false
                            },
                            {
                                "tabindex": "4",
                                "tags": [],
                                "clearOnHide": true,
                                "hidden": false,
                                "input": true,
                                "tableView": true,
                                "inputMask": "(999) 999-9999",
                                "label": "Phone Number",
                                "key": "phoneNumber",
                                "placeholder": "Enter your phone number",
                                "prefix": "",
                                "suffix": "",
                                "multiple": false,
                                "protected": false,
                                "unique": false,
                                "persistent": true,
                                "defaultValue": "",
                                "validate": {
                                    "required": false,
                                    "custom": "",
                                    "customPrivate": false,
                                    "strictDateValidation": false,
                                    "multiple": false,
                                    "unique": false,
                                    "minLength": "",
                                    "maxLength": "",
                                    "pattern": ""
                                },
                                "type": "phoneNumber",
                                "conditional": {
                                    "show": "",
                                    "when": null,
                                    "eq": ""
                                },
                                "customClass": "",
                                "refreshOn": "",
                                "redrawOn": "",
                                "modalEdit": false,
                                "dataGridLabel": false,
                                "labelPosition": "top",
                                "description": "",
                                "errorLabel": "",
                                "tooltip": "",
                                "hideLabel": false,
                                "disabled": false,
                                "autofocus": false,
                                "dbIndex": false,
                                "customDefaultValue": "",
                                "calculateValue": "",
                                "calculateServer": false,
                                "widget": {
                                    "type": "input"
                                },
                                "attributes": {},
                                "validateOn": "change",
                                "overlay": {
                                    "style": "",
                                    "left": "",
                                    "top": "",
                                    "width": "",
                                    "height": ""
                                },
                                "allowCalculateOverride": false,
                                "encrypted": false,
                                "showCharCount": false,
                                "showWordCount": false,
                                "properties": {},
                                "allowMultipleMasks": false,
                                "mask": false,
                                "inputType": "tel",
                                "inputFormat": "plain",
                                "spellcheck": true,
                                "inputMode": "decimal",
                                "id": "ecee6s",
                                "addons": [],
                                "displayMask": "",
                                "truncateMultipleSpaces": false
                            }
                        ],
                        "width": 6,
                        "offset": 0,
                        "push": 0,
                        "pull": 0,
                        "size": "md",
                        "currentWidth": 6
                    }
                ],
                "type": "columns",
                "conditional": {
                    "show": "",
                    "when": null,
                    "eq": ""
                },
                "key": "columns",
                "placeholder": "",
                "prefix": "",
                "customClass": "",
                "suffix": "",
                "multiple": false,
                "defaultValue": null,
                "protected": false,
                "unique": false,
                "persistent": false,
                "hidden": false,
                "clearOnHide": false,
                "refreshOn": "",
                "redrawOn": "",
                "tableView": false,
                "modalEdit": false,
                "label": "Columns",
                "dataGridLabel": false,
                "labelPosition": "top",
                "description": "",
                "errorLabel": "",
                "tooltip": "",
                "hideLabel": false,
                "tabindex": "",
                "disabled": false,
                "autofocus": false,
                "dbIndex": false,
                "customDefaultValue": "",
                "calculateValue": "",
                "calculateServer": false,
                "widget": null,
                "attributes": {},
                "validateOn": "change",
                "validate": {
                    "required": false,
                    "custom": "",
                    "customPrivate": false,
                    "strictDateValidation": false,
                    "multiple": false,
                    "unique": false
                },
                "overlay": {
                    "style": "",
                    "left": "",
                    "top": "",
                    "width": "",
                    "height": ""
                },
                "allowCalculateOverride": false,
                "encrypted": false,
                "showCharCount": false,
                "showWordCount": false,
                "properties": {},
                "allowMultipleMasks": false,
                "tree": false,
                "autoAdjust": false,
                "id": "e2i9r0c",
                "addons": [],
                "lazyLoad": false
            },
            {
                "tabindex": "5",
                "tags": [],
                "clearOnHide": true,
                "hidden": false,
                "input": true,
                "tableView": true,
                "label": "Survey",
                "key": "survey",
                "questions": [
                    {
                        "value": "howWouldYouRateTheFormIoPlatform",
                        "label": "How would you rate the Form.io platform?"
                    },
                    {
                        "value": "howWasCustomerSupport",
                        "label": "How was Customer Support?"
                    },
                    {
                        "value": "overallExperience",
                        "label": "Overall Experience?"
                    }
                ],
                "values": [
                    {
                        "value": "excellent",
                        "label": "Excellent"
                    },
                    {
                        "value": "great",
                        "label": "Great"
                    },
                    {
                        "value": "good",
                        "label": "Good"
                    },
                    {
                        "value": "average",
                        "label": "Average"
                    },
                    {
                        "value": "poor",
                        "label": "Poor"
                    }
                ],
                "defaultValue": "",
                "protected": false,
                "persistent": true,
                "validate": {
                    "required": false,
                    "custom": "",
                    "customPrivate": false,
                    "strictDateValidation": false,
                    "multiple": false,
                    "unique": false
                },
                "type": "survey",
                "conditional": {
                    "show": "",
                    "when": null,
                    "eq": ""
                },
                "placeholder": "",
                "prefix": "",
                "customClass": "",
                "suffix": "",
                "multiple": false,
                "unique": false,
                "refreshOn": "",
                "redrawOn": "",
                "modalEdit": false,
                "dataGridLabel": false,
                "labelPosition": "top",
                "description": "",
                "errorLabel": "",
                "tooltip": "",
                "hideLabel": false,
                "disabled": false,
                "autofocus": false,
                "dbIndex": false,
                "customDefaultValue": "",
                "calculateValue": "",
                "calculateServer": false,
                "widget": null,
                "attributes": {},
                "validateOn": "change",
                "overlay": {
                    "style": "",
                    "left": "",
                    "top": "",
                    "width": "",
                    "height": ""
                },
                "allowCalculateOverride": false,
                "encrypted": false,
                "showCharCount": false,
                "showWordCount": false,
                "properties": {},
                "allowMultipleMasks": false,
                "id": "ek3vrnp",
                "addons": []
            },
            {
                "tags": [],
                "clearOnHide": true,
                "hidden": false,
                "input": true,
                "tableView": true,
                "label": "Signature",
                "key": "signature",
                "placeholder": "",
                "footer": "Sign above",
                "width": "100%",
                "height": "150px",
                "penColor": "black",
                "backgroundColor": "rgb(245,245,235)",
                "minWidth": "0.5",
                "maxWidth": "2.5",
                "protected": false,
                "persistent": true,
                "validate": {
                    "required": false,
                    "custom": "",
                    "customPrivate": false,
                    "strictDateValidation": false,
                    "multiple": false,
                    "unique": false
                },
                "type": "signature",
                "hideLabel": true,
                "conditional": {
                    "show": "",
                    "when": null,
                    "eq": ""
                },
                "prefix": "",
                "customClass": "",
                "suffix": "",
                "multiple": false,
                "defaultValue": null,
                "unique": false,
                "refreshOn": "",
                "redrawOn": "",
                "modalEdit": false,
                "dataGridLabel": false,
                "labelPosition": "top",
                "description": "",
                "errorLabel": "",
                "tooltip": "",
                "tabindex": "",
                "disabled": false,
                "autofocus": false,
                "dbIndex": false,
                "customDefaultValue": "",
                "calculateValue": "",
                "calculateServer": false,
                "widget": {
                    "type": "input"
                },
                "attributes": {},
                "validateOn": "change",
                "overlay": {
                    "style": "",
                    "left": "",
                    "top": "",
                    "width": "",
                    "height": ""
                },
                "allowCalculateOverride": false,
                "encrypted": false,
                "showCharCount": false,
                "showWordCount": false,
                "properties": {},
                "allowMultipleMasks": false,
                "id": "e4zac8j",
                "addons": [],
                "keepOverlayRatio": true
            },
            {
                "properties": {
                    "": ""
                },
                "conditional": {
                    "eq": "",
                    "when": null,
                    "show": ""
                },
                "tags": [],
                "hideLabel": false,
                "type": "editgrid",
                "clearOnHide": true,
                "hidden": false,
                "persistent": true,
                "protected": false,
                "key": "panelDataGrid",
                "label": "Data Grid",
                "tableView": true,
                "components": [
                    {
                        "properties": {
                            "": ""
                        },
                        "tags": [],
                        "labelPosition": "top",
                        "hideLabel": true,
                        "type": "textfield",
                        "conditional": {
                            "eq": "",
                            "when": null,
                            "show": ""
                        },
                        "validate": {
                            "customPrivate": false,
                            "custom": "",
                            "pattern": "",
                            "maxLength": "",
                            "minLength": "",
                            "required": false
                        },
                        "clearOnHide": true,
                        "hidden": false,
                        "persistent": true,
                        "unique": false,
                        "protected": false,
                        "defaultValue": "",
                        "multiple": false,
                        "suffix": "",
                        "prefix": "",
                        "placeholder": "",
                        "key": "panelDataGridA",
                        "label": "A",
                        "inputMask": "",
                        "inputType": "text",
                        "tableView": true,
                        "input": true,
                        "autofocus": false,
                        "spellcheck": true,
                        "inDataGrid": true
                    },
                    {
                        "properties": {
                            "": ""
                        },
                        "tags": [],
                        "labelPosition": "top",
                        "hideLabel": true,
                        "type": "textfield",
                        "conditional": {
                            "eq": "",
                            "when": null,
                            "show": ""
                        },
                        "validate": {
                            "customPrivate": false,
                            "custom": "",
                            "pattern": "",
                            "maxLength": "",
                            "minLength": "",
                            "required": false
                        },
                        "clearOnHide": true,
                        "hidden": false,
                        "persistent": true,
                        "unique": false,
                        "protected": false,
                        "defaultValue": "",
                        "multiple": false,
                        "suffix": "",
                        "prefix": "",
                        "placeholder": "",
                        "key": "panelDataGridB",
                        "label": "B",
                        "inputMask": "",
                        "inputType": "text",
                        "tableView": true,
                        "input": true,
                        "autofocus": false,
                        "spellcheck": true,
                        "inDataGrid": true
                    },
                    {
                        "properties": {
                            "": ""
                        },
                        "tags": [],
                        "labelPosition": "top",
                        "hideLabel": true,
                        "type": "textfield",
                        "conditional": {
                            "eq": "",
                            "when": null,
                            "show": ""
                        },
                        "validate": {
                            "customPrivate": false,
                            "custom": "",
                            "pattern": "",
                            "maxLength": "",
                            "minLength": "",
                            "required": false
                        },
                        "clearOnHide": true,
                        "hidden": false,
                        "persistent": true,
                        "unique": false,
                        "protected": false,
                        "defaultValue": "",
                        "multiple": false,
                        "suffix": "",
                        "prefix": "",
                        "placeholder": "",
                        "key": "panelDataGridC",
                        "label": "C",
                        "inputMask": "",
                        "inputType": "text",
                        "tableView": true,
                        "input": true,
                        "autofocus": false,
                        "spellcheck": true,
                        "inDataGrid": true
                    },
                    {
                        "properties": {
                            "": ""
                        },
                        "tags": [],
                        "labelPosition": "top",
                        "hideLabel": true,
                        "type": "textfield",
                        "conditional": {
                            "eq": "",
                            "when": null,
                            "show": ""
                        },
                        "validate": {
                            "customPrivate": false,
                            "custom": "",
                            "pattern": "",
                            "maxLength": "",
                            "minLength": "",
                            "required": false
                        },
                        "clearOnHide": true,
                        "hidden": false,
                        "persistent": true,
                        "unique": false,
                        "protected": false,
                        "defaultValue": "",
                        "multiple": false,
                        "suffix": "",
                        "prefix": "",
                        "placeholder": "",
                        "key": "panelDataGridD",
                        "label": "D",
                        "inputMask": "",
                        "inputType": "text",
                        "tableView": true,
                        "input": true,
                        "autofocus": false,
                        "spellcheck": true,
                        "inDataGrid": true
                    }
                ],
                "tree": true,
                "input": true,
                "autofocus": false
            },
        ],
    }

    change(event) {
        console.log(event);
    }
}
