import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISuministro, Suministro } from 'app/shared/model/suministro.model';
import { SuministroService } from './suministro.service';
import { SuministroComponent } from './suministro.component';
import { SuministroDetailComponent } from './suministro-detail.component';
import { SuministroUpdateComponent } from './suministro-update.component';

@Injectable({ providedIn: 'root' })
export class SuministroResolve implements Resolve<ISuministro> {
  constructor(private service: SuministroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISuministro> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((suministro: HttpResponse<Suministro>) => {
          if (suministro.body) {
            return of(suministro.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Suministro());
  }
}

export const suministroRoute: Routes = [
  {
    path: '',
    component: SuministroComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ofVApp.suministro.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SuministroDetailComponent,
    resolve: {
      suministro: SuministroResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ofVApp.suministro.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SuministroUpdateComponent,
    resolve: {
      suministro: SuministroResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ofVApp.suministro.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SuministroUpdateComponent,
    resolve: {
      suministro: SuministroResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ofVApp.suministro.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
