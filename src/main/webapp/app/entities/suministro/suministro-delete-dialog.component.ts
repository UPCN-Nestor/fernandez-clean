import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISuministro } from 'app/shared/model/suministro.model';
import { SuministroService } from './suministro.service';

@Component({
  templateUrl: './suministro-delete-dialog.component.html'
})
export class SuministroDeleteDialogComponent {
  suministro?: ISuministro;

  constructor(
    protected suministroService: SuministroService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.suministroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('suministroListModification');
      this.activeModal.close();
    });
  }
}
