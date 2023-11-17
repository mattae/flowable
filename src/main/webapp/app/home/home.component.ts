import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import * as BpmnJS from 'bpmn-js/dist/bpmn-modeler.production.min.js';
import BpmnModeler from 'bpmn-js/lib/Modeler';
import * as Flowable from '../modules/bpmn-js-properties-panel-flowable';
import {
    BpmnPropertiesPanelModule,
    BpmnPropertiesProviderModule,
    CamundaPlatformPropertiesProviderModule
} from 'bpmn-js-properties-panel';
@Component({
    selector: 'app-home',
    templateUrl: './home.component.html'
})
export class HomeComponent implements AfterViewInit{
    @ViewChild('canvas', {static: true}) container: ElementRef;
    private bpmnJS: BpmnJS;
    private bpmnModeler: any;
    constructor() {
    }

    ngAfterViewInit(): void {
        this.bpmnJS = new BpmnModeler({
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
        this.bpmnJS.createDiagram();
    }
}
