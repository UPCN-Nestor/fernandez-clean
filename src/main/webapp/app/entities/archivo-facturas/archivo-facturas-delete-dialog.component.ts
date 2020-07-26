import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArchivoFacturas } from 'app/shared/model/archivo-facturas.model';
import { ArchivoFacturasService } from './archivo-facturas.service';

@Component({
  templateUrl: './archivo-facturas-delete-dialog.component.html'
})
export class ArchivoFacturasDeleteDialogComponent {
  archivoFacturas?: IArchivoFacturas;

  constructor(
    protected archivoFacturasService: ArchivoFacturasService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.archivoFacturasService.delete(id).subscribe(() => {
      this.eventManager.broadcast('archivoFacturasListModification');
      this.activeModal.close();
    });
  }
}
