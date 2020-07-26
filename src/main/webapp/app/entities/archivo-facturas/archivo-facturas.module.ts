import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OfVSharedModule } from 'app/shared/shared.module';
import { ArchivoFacturasComponent } from './archivo-facturas.component';
import { ArchivoFacturasDetailComponent } from './archivo-facturas-detail.component';
import { ArchivoFacturasUpdateComponent } from './archivo-facturas-update.component';
import { ArchivoFacturasDeleteDialogComponent } from './archivo-facturas-delete-dialog.component';
import { archivoFacturasRoute } from './archivo-facturas.route';

@NgModule({
  imports: [OfVSharedModule, RouterModule.forChild(archivoFacturasRoute)],
  declarations: [
    ArchivoFacturasComponent,
    ArchivoFacturasDetailComponent,
    ArchivoFacturasUpdateComponent,
    ArchivoFacturasDeleteDialogComponent
  ],
  entryComponents: [ArchivoFacturasDeleteDialogComponent]
})
export class OfVArchivoFacturasModule {}
