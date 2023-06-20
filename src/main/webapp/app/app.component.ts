import { Component } from '@angular/core';
import { WorkService } from './flowable/services/work.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html'
})
export class AppComponent {
    constructor(private workService: WorkService) {
        workService.getWorkForm().subscribe(res => console.log('Work forms', res))
    }
}
