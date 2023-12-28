import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    EventEmitter,
    Input,
    OnInit,
    Output
} from '@angular/core';
import { Definition, WorkForm } from '../../model/work.model';
import { MatIconModule } from '@angular/material/icon';
import { NgClass, NgForOf, NgIf } from '@angular/common';
import { TranslocoModule } from '@ngneat/transloco';

export type DefinitionEvent = {
    definition: Definition;
}

@Component({
    selector: 'app-def',
    templateUrl: './app.component.html',
    standalone: true,
    changeDetection: ChangeDetectionStrategy.OnPush,
    styleUrls: ['./app.component.scss'],
    imports: [
        MatIconModule,
        NgForOf,
        NgIf,
        NgClass,
        TranslocoModule
    ]
})
export class AppDefComponent implements OnInit {
    @Input({
        required: true
    })
    workForm: WorkForm
    @Output()
    definition = new EventEmitter<DefinitionEvent>();
    opened = true;

    constructor(private _cdr: ChangeDetectorRef) {
    }

    toggle() {
        this.opened = !this.opened;
        this._cdr.markForCheck();
    }

    ngOnInit(): void {
        this._cdr.markForCheck();
    }

    definitionClicked(definition: Definition): void {
        this.definition.emit({
            definition: definition
        });
    }
}
