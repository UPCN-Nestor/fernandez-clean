import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OfVSharedModule } from 'app/shared/shared.module';
import { FacturaComponent } from './factura.component';
import { FacturaDetailComponent } from './factura-detail.component';
import { FacturaUpdateComponent } from './factura-update.component';
import { FacturaDeleteDialogComponent } from './factura-delete-dialog.component';
import { facturaRoute } from './factura.route';
import { CardModule } from 'primeng/card';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [OfVSharedModule, RouterModule.forChild(facturaRoute), CardModule, NgbModalModule],
  declarations: [FacturaComponent, FacturaDetailComponent, FacturaUpdateComponent, FacturaDeleteDialogComponent],
  entryComponents: [FacturaDeleteDialogComponent]
})
export class OfVFacturaModule {}
