import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFactura, Factura } from 'app/shared/model/factura.model';
import { FacturaService } from './factura.service';
import { IArchivoFacturas } from 'app/shared/model/archivo-facturas.model';
import { ArchivoFacturasService } from 'app/entities/archivo-facturas/archivo-facturas.service';

@Component({
  selector: 'jhi-factura-update',
  templateUrl: './factura-update.component.html'
})
export class FacturaUpdateComponent implements OnInit {
  isSaving = false;
  archivofacturas: IArchivoFacturas[] = [];
  vencimiento1Dp: any;
  vencimiento2Dp: any;

  editForm = this.fb.group({
    id: [],
    suministro: [],
    usuario: [],
    inmueble: [],
    periodo: [],
    numero: [],
    vencimiento1: [],
    vencimiento2: [],
    importe1: [],
    importe2: [],
    servicio: [],
    tarifa: [],
    archivopdf: [],
    estado: [],
    dni: [],
    socio: [],
    archivoFacturas: []
  });

  constructor(
    protected facturaService: FacturaService,
    protected archivoFacturasService: ArchivoFacturasService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factura }) => {
      this.updateForm(factura);

      this.archivoFacturasService.query().subscribe((res: HttpResponse<IArchivoFacturas[]>) => (this.archivofacturas = res.body || []));
    });
  }

  updateForm(factura: IFactura): void {
    this.editForm.patchValue({
      id: factura.id,
      suministro: factura.suministro,
      usuario: factura.usuario,
      inmueble: factura.inmueble,
      periodo: factura.periodo,
      numero: factura.numero,
      vencimiento1: factura.vencimiento1,
      vencimiento2: factura.vencimiento2,
      importe1: factura.importe1,
      importe2: factura.importe2,
      servicio: factura.servicio,
      tarifa: factura.tarifa,
      archivopdf: factura.archivopdf,
      estado: factura.estado,
      dni: factura.dni,
      socio: factura.socio,
      archivoFacturas: factura.archivoFacturas
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const factura = this.createFromForm();
    if (factura.id !== undefined) {
      this.subscribeToSaveResponse(this.facturaService.update(factura));
    } else {
      this.subscribeToSaveResponse(this.facturaService.create(factura));
    }
  }

  private createFromForm(): IFactura {
    return {
      ...new Factura(),
      id: this.editForm.get(['id'])!.value,
      suministro: this.editForm.get(['suministro'])!.value,
      usuario: this.editForm.get(['usuario'])!.value,
      inmueble: this.editForm.get(['inmueble'])!.value,
      periodo: this.editForm.get(['periodo'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      vencimiento1: this.editForm.get(['vencimiento1'])!.value,
      vencimiento2: this.editForm.get(['vencimiento2'])!.value,
      importe1: this.editForm.get(['importe1'])!.value,
      importe2: this.editForm.get(['importe2'])!.value,
      servicio: this.editForm.get(['servicio'])!.value,
      tarifa: this.editForm.get(['tarifa'])!.value,
      archivopdf: this.editForm.get(['archivopdf'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      dni: this.editForm.get(['dni'])!.value,
      socio: this.editForm.get(['socio'])!.value,
      archivoFacturas: this.editForm.get(['archivoFacturas'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFactura>>): void {
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

  trackById(index: number, item: IArchivoFacturas): any {
    return item.id;
  }
}
