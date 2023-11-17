import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    EventEmitter,
    Input,
    OnDestroy,
    OnInit,
    Output
} from '@angular/core';
import { Observable, Observer, share, Subject, takeUntil } from 'rxjs';
import { CommentService } from '../services/comment.service';
import { Comment } from '../model/comment.model';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { NgForOf, NgIf } from '@angular/common';
import { TranslocoModule } from '@ngneat/transloco';
import { MatListModule } from '@angular/material/list';
import { DEFAULT_TOOLBAR, Editor, NgxEditorModule, toHTML, Toolbar } from 'ngx-editor';
import { CovalentCommonModule } from '@covalent/core/common';
import { MatLineModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { User } from '../model/common.model';
import { FuseScrollbarDirective } from '@mattae/angular-shared';

@Component({
    selector: 'flw-comments',
    templateUrl: './comments.component.html',
    standalone: true,
    changeDetection: ChangeDetectionStrategy.OnPush,
    imports: [
        MatInputModule,
        FormsModule,
        NgIf,
        TranslocoModule,
        NgForOf,
        MatListModule,
        NgxEditorModule,
        CovalentCommonModule,
        MatLineModule,
        MatButtonModule,
        MatIconModule,
        FuseScrollbarDirective
    ],
    styles: [
        `
            .NgxEditor__Content {
                @apply h-36
            }
        `
    ]
})
export class CommentsComponent implements OnInit, OnDestroy {
    @Input()
    processInstanceId: string;
    @Input()
    caseInstanceId: string;
    @Input()
    taskId: string;

    /** Should the comments be read-only? */
    @Input()
    readOnly: boolean = false;
    @Output()
    count = new EventEmitter<number>();
    editor: Editor;
    toolbar: Toolbar = DEFAULT_TOOLBAR;

    private commentObserver: Observer<Comment>;
    comment$: Observable<Comment>;

    private onDestroy$ = new Subject<boolean>();

    message: string;

    beingAdded: boolean = false;
    comments: Comment [] = [];

    constructor(private _commentService: CommentService, private _changeDetectorRef: ChangeDetectorRef) {
        this.comment$ = new Observable<Comment>(observer => this.commentObserver = observer).pipe(share());
        this.comment$
            .pipe(takeUntil(this.onDestroy$))
            .subscribe(comment => this.comments.push(comment));
    }

    ngOnInit(): void {
        this.editor = new Editor();
        this.editor.valueChanges.subscribe(value => this.message = toHTML(value));
        this.resetComments();
        if (this.processInstanceId) {
            this.getProcessComments()
        } else if (this.taskId) {
            this.getTaskComments();
        }
    }

    ngOnDestroy() {
        this.editor.destroy();
        this.onDestroy$.next(true);
        this.onDestroy$.complete();
    }

    getTaskComments() {
        this._commentService.getTaskComments(this.taskId).subscribe(
            (res) => {
                let data = res.data;
                data = data.sort((comment1: Comment, comment2: Comment) => {
                    return comment1.created > comment2.created ? -1 : comment1.created < comment2.created ? 1 : 0;
                });
                this.count.emit(data.length);
                data.forEach((comment) => {
                    this.commentObserver.next(comment);
                });

                this._changeDetectorRef.markForCheck();
            }
        );
    }

    getProcessComments() {
        this._commentService.getProcessComments(this.processInstanceId).subscribe(
            (res) => {
                let data = res.data;
                data = data.sort((comment1: Comment, comment2: Comment) => {
                    return comment1.created > comment2.created ? -1 : comment1.created < comment2.created ? 1 : 0;
                });
                this.count.emit(data.length);
                data.forEach((comment) => {
                    this.commentObserver.next(comment);
                });

                this._changeDetectorRef.markForCheck();
            }
        );
    }

    private resetComments(): void {
        this.comments = [];
    }

    add(): void {
        if (this.message && this.message.trim() && !this.beingAdded) {
            this.beingAdded = true;
            if (this.processInstanceId) {
                this._commentService.addProcessComment(this.processInstanceId, {message: this.message})
                    .subscribe({
                            next: (_) => {
                                this.editor.setContent('');
                                this.resetComments();
                                this.getProcessComments();
                                this.beingAdded = false;
                            },
                            error: (err: any) => {
                                this.beingAdded = false;
                            }
                        }
                    );
            } else if (this.taskId) {
                this._commentService.addTaskComment(this.taskId, {message: this.message})
                    .subscribe({
                            next: (_) => {
                                this.editor.setContent('');
                                this.resetComments();
                                this.getTaskComments();
                                this.beingAdded = false;
                            },
                            error: (err: any) => {
                                this.beingAdded = false;
                            }
                        }
                    );
            }
        }
    }

    clear(): void {
        this.message = '';
    }

    isReadOnly(): boolean {
        return this.readOnly;
    }

    getInitials(user: User) {
        if (user) {
            return user.firstName.charAt(0).toUpperCase() + user.lastName.charAt(0).toUpperCase();
        }
        return '';
    }
}
