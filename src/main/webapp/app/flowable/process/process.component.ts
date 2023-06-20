import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { TranslocoModule } from '@ngneat/transloco';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { ProcessInstance } from '../model/process.model';
import { MatFormioModule } from '../../formio/angular-material-formio.module';
import { ProcessService } from '../services/process.service';
import { FormModel } from '../model/common.model';
import { NgIf } from '@angular/common';
import { CovalentCommonModule } from '@covalent/core/common';

@Component({
    selector: 'flowable-process',
    templateUrl: './process.component.html',
    standalone: true,
    imports: [
        MatIconModule,
        TranslocoModule,
        RouterLink,
        MatButtonModule,
        MatTabsModule,
        MatFormioModule,
        NgIf,
        CovalentCommonModule
    ]
})
export class ProcessComponent implements OnInit {
    instance: ProcessInstance;
    form: FormModel;

    constructor(private _router: Router, private _activatedRoute: ActivatedRoute, private _processService: ProcessService) {
    }

    ngOnInit(): void {
        this._activatedRoute.data.subscribe(({instance}) => {
            this.instance = instance;
            if (this.instance.startFormDefined) {
                this._processService.getProcessInstanceStartForm(this.instance.id).subscribe(form => {
                    this.form = form;
                });
            }
        });
    }

}
