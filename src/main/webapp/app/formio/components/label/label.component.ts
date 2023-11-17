import { Component, Input } from '@angular/core';
import { NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTooltipModule } from '@angular/material/tooltip';
import { TranslocoModule } from '@ngneat/transloco';

@Component({
    // tslint:disable-next-line:component-selector
    selector: 'span[matFormioLabel]',
    templateUrl: './label.component.html',
    styleUrls: ['./label.component.css'],
    imports: [
        NgIf,
        MatIconModule,
        MatFormFieldModule,
        MatTooltipModule,
        TranslocoModule,
    ],
    standalone: true
})
export class LabelComponent {
    @Input() instance;
}
