import { Component, OnInit } from '@angular/core';
import { WorkService } from '../services/work.service';
import { WorkForm } from '../model/work.model';

@Component({
    selector: 'app-def',
    templateUrl: './app.component.html',
    standalone: true,
    imports: [],
    styleUrls: ['./app.component.scss']
})
export class AppDefComponent implements OnInit {
    workForms: WorkForm[];

    constructor(private workService: WorkService) {

    }

    ngOnInit(): void {
        this.workService.getWorkForm().subscribe(res => this.workForms = res);
    }

}
