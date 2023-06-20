import { Component, Input, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { NgForOf } from '@angular/common';
import { UserService } from '../services/user.service';

@Component({
    selector: 'people',
    templateUrl: './people.component.html',
    standalone: true,
    imports: [
        FormsModule,
        MatChipsModule,
        MatIconModule,
        NgForOf,
        ReactiveFormsModule
    ]
})
export class PeopleComponent implements OnInit {
    @Input()
    processInstanceId: string;
    @Input()
    taskId: string;
    constructor(private _userService: UserService) {
    }

    ngOnInit(): void {
    }
}
