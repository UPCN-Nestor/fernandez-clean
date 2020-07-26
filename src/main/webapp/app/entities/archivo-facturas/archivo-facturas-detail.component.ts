import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IArchivoFacturas } from 'app/shared/model/archivo-facturas.model';

@Component({
  selector: 'jhi-archivo-facturas-detail',
  templateUrl: './archivo-facturas-detail.component.html'
})
export class ArchivoFacturasDetailComponent implements OnInit {
  archivoFacturas: IArchivoFacturas | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ archivoFacturas }) => (this.archivoFacturas = archivoFacturas));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
