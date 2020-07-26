import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArchivoFacturas, ArchivoFacturas } from 'app/shared/model/archivo-facturas.model';
import { ArchivoFacturasService } from './archivo-facturas.service';
import { ArchivoFacturasComponent } from './archivo-facturas.component';
import { ArchivoFacturasDetailComponent } from './archivo-facturas-detail.component';
import { ArchivoFacturasUpdateComponent } from './archivo-facturas-update.component';

@Injectable({ providedIn: 'root' })
export class ArchivoFacturasResolve implements Resolve<IArchivoFacturas> {
  constructor(private service: ArchivoFacturasService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArchivoFacturas> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((archivoFacturas: HttpResponse<ArchivoFacturas>) => {
          if (archivoFacturas.body) {
            return of(archivoFacturas.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ArchivoFacturas());
  }
}

export const archivoFacturasRoute: Routes = [
  {
    path: '',
    component: ArchivoFacturasComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ofVApp.archivoFacturas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ArchivoFacturasDetailComponent,
    resolve: {
      archivoFacturas: ArchivoFacturasResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ofVApp.archivoFacturas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ArchivoFacturasUpdateComponent,
    resolve: {
      archivoFacturas: ArchivoFacturasResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ofVApp.archivoFacturas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ArchivoFacturasUpdateComponent,
    resolve: {
      archivoFacturas: ArchivoFacturasResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ofVApp.archivoFacturas.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
