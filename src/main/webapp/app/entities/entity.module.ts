import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'factura',
        loadChildren: () => import('./factura/factura.module').then(m => m.OfVFacturaModule)
      },
      {
        path: 'archivo-facturas',
        loadChildren: () => import('./archivo-facturas/archivo-facturas.module').then(m => m.OfVArchivoFacturasModule)
      },
      {
        path: 'suministro',
        loadChildren: () => import('./suministro/suministro.module').then(m => m.OfVSuministroModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class OfVEntityModule {}
