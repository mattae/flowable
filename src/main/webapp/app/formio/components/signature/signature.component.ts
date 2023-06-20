import { AfterViewInit, Component, ElementRef, OnDestroy, Renderer2, ViewChild } from '@angular/core';
import { NgxSignatureOptions, NgxSignaturePadComponent, NgxSignaturePadModule } from '@eve-sama/ngx-signature-pad';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
    selector: 'mat-formio-signature',
    template: `
        <div *ngIf="visible">
            <mat-label *ngIf="instance.component.label && !instance.component.hideLabel">
                <span>{{instance.component.label}}</span>
            </mat-label>

            <div class="w-full p-1 bg-amber-100">
                <ng-container *ngIf="!disabled">
                    <button mat-icon-button [ngStyle]="{position: 'absolute'}" (click)="signature.clear()">
                        <mat-icon>refresh</mat-icon>
                    </button>
                    <ngx-signature-pad class="w-full"
                                       #signature
                                       (beginSign)="onBeginSign()"
                                       (endSign)="onEndSign()">
                    </ngx-signature-pad>
                </ng-container>
                <ng-container *ngIf="disabled">
                    <img #img [src]="value" class="w-full p-2" alt="signature"/>
                </ng-container>
            </div>

            <div *ngIf="instance.component.footer"
                 class="signature-pad-footer self-center flex-row flex"
            >
                <mat-hint>{{ instance.t(instance.component.footer) }}</mat-hint>
            </div>
        </div>
    `,
    standalone: true,
    imports: [
        CommonModule,
        MatFormFieldModule,
        MatButtonModule,
        MatIconModule,
        NgxSignaturePadModule
    ]
})
export class MaterialSignatureComponent implements AfterViewInit, OnDestroy {
    @ViewChild('signature') signature: NgxSignaturePadComponent;
    public options: NgxSignatureOptions = {
        backgroundColor: '#e1e1c7',
        width: 854,
        height: 480,
        css: {
            'border-radius': '16px'
        }
    };
    visible: boolean = true;
    instance: any;
    disabled: boolean = false;
    value: any;

    observer = new ResizeObserver(entries => {
        entries.forEach(entry => {
            this.resizeCanvas();
        });
    });

    constructor(public element: ElementRef, private sanitizer: DomSanitizer, private renderer: Renderer2) {
    }

    ngOnInit() {
        this.observer.observe(this.element.nativeElement);
    }

    ngOnDestroy() {
        this.observer.disconnect();
    }

    onBeginSign(): void {
    }

    onEndSign(): void {
        this.instance.setValue(this.signature.toDataURL());
    }

    setDisabled(disabled) {
        this.disabled = disabled;
    }

    setVisible(visible) {
        this.visible = visible;
    }

    setValue(value) {
        if (value) {
            this.value = this.sanitizer.bypassSecurityTrustResourceUrl(value);
            this.signature.fromDataURL(value);
        }
    }

    setInstance(instance: any) {
        this.instance = instance;
    }

    ngAfterViewInit() {
        this.resizeCanvas();
    }

    resizeCanvas(): void {
        const parentContainerWidth = this.element.nativeElement.offsetWidth;
        const canvas = this.element.nativeElement.querySelector('canvas');
        if (canvas) {
            canvas.width = parentContainerWidth - 20;
            this.renderer.addClass(canvas, 'mx-auto');
            this.renderer.addClass(canvas, 'p-2');
        }
    }
}
