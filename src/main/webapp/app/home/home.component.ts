import {
    AfterViewInit,
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    ViewChild
} from '@angular/core';
import * as BpmnJS from 'bpmn-js/dist/bpmn-modeler.production.min.js';
import { MatFormioComponent } from '@mattae/angular-shared';

@Component({
    selector: 'app-home',
    imports: [
        MatFormioComponent
    ],
    templateUrl: './home.component.html',
    standalone: true,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class HomeComponent implements AfterViewInit {
    @ViewChild('canvas', {static: true}) container: ElementRef;
    private bpmnJS: BpmnJS;
    private bpmnModeler: any;
    form = {
        "type": "form",
        "tags": [],
        "owner": "553dbfc08d22d5cb1a7024f2",
        "components": [
            {
                "input": false,
                "html": "<h1><a href=\"https://form.io\">Form.io</a> Example Form</h1>\n\n<p>This is a dynamically rendered JSON form&nbsp;built with <a href=\"https://form.io\">Form.io</a>. Using a simple&nbsp;drag-and-drop form builder, you can create any form that includes e-signatures, wysiwyg editors, date fields, layout components, data grids, surveys, etc.</p>\n",
                "type": "content",

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
                                    "customPrivate": false
                                },
                                "type": "textfield",
                                "autofocus": false,
                                "spellcheck": true
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
                                "kickbox": {
                                    "enabled": false
                                },
                                "autofocus": false
                            }
                        ],
                        "width": 6,
                        "offset": 0,
                        "push": 0,
                        "pull": 0
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
                                    "customPrivate": false
                                },
                                "type": "textfield",
                                "autofocus": false,
                                "spellcheck": true
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
                                    "required": false
                                },
                                "type": "phoneNumber",
                                "autofocus": false,
                                "inputType": "tel"
                            }
                        ],
                        "width": 6,
                        "offset": 0,
                        "push": 0,
                        "pull": 0
                    }
                ],
                "type": "columns",
                "clearOnHide": false,
                "label": "Columns",
                "hideLabel": true,
                "tableView": false
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
                    "customPrivate": false
                },
                "type": "survey"
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
                    "required": false
                },
                "type": "signature",
                "hideLabel": true
            },
            {
                "tabindex": "6",
                "tags": [],
                "input": true,
                "label": "Submit",
                "tableView": false,
                "key": "submit",
                "size": "md",
                "leftIcon": "",
                "rightIcon": "",
                "block": false,
                "action": "submit",
                "disableOnInvalid": true,
                "theme": "primary",
                "type": "button",
                "autofocus": false
            }
        ],
        "title": "Example",
        "display": "form"
    }

    constructor(private cdr: ChangeDetectorRef) {
    }

    ngAfterViewInit(): void {
        this.cdr.markForCheck()
        /* this.bpmnJS = new BpmnModeler({
             container: '#canvas',
             propertiesPanel: {
                 parent: '#properties'
             },
             additionalModules: [
                 BpmnPropertiesPanelModule,
                 BpmnPropertiesProviderModule,
                 CamundaPlatformPropertiesProviderModule,
                 Flowable
             ],
             moddleExtensions: {
             }
         });

         this.bpmnJS.on('import.done', ({error}) => {
             if (!error) {
                 this.bpmnJS.get('canvas').zoom('fit-viewport');
             }
         });
         this.bpmnJS.createDiagram();*/
    }
    change(event) {
        console.log('Change', event)
    }
}
