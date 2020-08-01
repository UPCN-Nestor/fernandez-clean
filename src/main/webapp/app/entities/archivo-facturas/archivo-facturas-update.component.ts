import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IArchivoFacturas, ArchivoFacturas } from 'app/shared/model/archivo-facturas.model';
import { ArchivoFacturasService } from './archivo-facturas.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import * as moment from 'moment';

@Component({
  selector: 'jhi-archivo-facturas-update',
  templateUrl: './archivo-facturas-update.component.html'
})
export class ArchivoFacturasUpdateComponent implements OnInit {
  isSaving = false;
  fechaDp: any;

  @ViewChild('file') file: any;

  fileName = '';

  editForm = this.fb.group({
    id: [],
    nombre: [],
    fecha: [],
    archivoBlob: [],
    archivoBlobContentType: [],
    archivoCsv: [],
    archivoCsvContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected archivoFacturasService: ArchivoFacturasService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ archivoFacturas }) => {
      this.updateForm(archivoFacturas);
    });
  }

  updateForm(archivoFacturas: IArchivoFacturas): void {
    this.editForm.patchValue({
      id: archivoFacturas.id,
      nombre: archivoFacturas.nombre,
      fecha: archivoFacturas.fecha,
      archivoBlob: archivoFacturas.archivoBlob,
      archivoBlobContentType: archivoFacturas.archivoBlobContentType,
      archivoCsv: archivoFacturas.archivoCsv,
      archivoCsvContentType: archivoFacturas.archivoCsvContentType
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    const input = event.srcElement;
    this.fileName = input.files[0].name;

    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('ofVApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const archivoFacturas = this.createFromForm();
    if (archivoFacturas.id !== undefined) {
      this.subscribeToSaveResponse(this.archivoFacturasService.update(archivoFacturas));
    } else {
      this.subscribeToSaveResponse(this.archivoFacturasService.create(archivoFacturas));
    }
  }

  private createFromForm(): IArchivoFacturas {
    return {
      ...new ArchivoFacturas(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.fileName,
      fecha: moment(),
      archivoBlobContentType: this.editForm.get(['archivoBlobContentType'])!.value,
      archivoBlob: this.editForm.get(['archivoBlob'])!.value,
      archivoCsvContentType: this.editForm.get(['archivoCsvContentType'])!.value,
      archivoCsv: this.editForm.get(['archivoCsv'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArchivoFacturas>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
