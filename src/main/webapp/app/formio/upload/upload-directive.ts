import { Directive, EventEmitter, HostBinding, HostListener, Input, Output } from "@angular/core";
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

export interface FileHandle {
    file: File,
    url: SafeUrl
}

@Directive({
    selector: '[upload]',
    standalone: true
})
export class DragUploadDirective {
    @Output() files: EventEmitter<FileHandle[]> = new EventEmitter();
    @Input() accept: String;
    @Input() multiple: boolean;
    @Input() maxSize: number;

    @HostBinding("style.background") private background = "#eee";

    constructor(private sanitizer: DomSanitizer) {
    }

    @HostListener("dragover", ["$event"])
    public onDragOver(evt: DragEvent) {
        evt.preventDefault();
        evt.stopPropagation();
        this.background = "#999";
    }

    @HostListener("dragleave", ["$event"])
    public onDragLeave(evt: DragEvent) {
        evt.preventDefault();
        evt.stopPropagation();
        this.background = "#eee";
    }

    @HostListener('drop', ['$event'])
    public onDrop(evt: DragEvent) {
        evt.preventDefault();
        evt.stopPropagation();
        this.background = '#eee';

        let files: FileHandle[] = [];
        const fileList: FileList = this.filterMultipleFiles(evt.dataTransfer.files);
        if (fileList) {
            const accepted: File[] = this.filterSize(this.filterAcceptedTypes(Array.from(evt.dataTransfer.files)));
            for (let i = 0; i < accepted.length; i++) {
                const file = evt.dataTransfer.files[i];
                const url = this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(file));
                files.push({file, url});
            }
            if (files.length > 0) {
                this.files.emit(files);
            }
        }
    }

    filterAcceptedTypes(files: File[]): File[] {
        const allowedFiles: File[] = [];
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            if (!this.accept || this.accept.includes(file.type)) {
                allowedFiles.push(file);
            }
        }
        return allowedFiles;
    }

    filterSize(files: File[]): File[] {
        const allowedFiles: File[] = [];
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            if (!this.maxSize || file.size <= this.maxSize) {
                allowedFiles.push(file);
            }
        }
        return allowedFiles;
    }

    filterMultipleFiles(files: FileList): FileList | undefined {
        if (!this.multiple && files.length > 1) {
            return undefined;
        }
        return files
    }
}
