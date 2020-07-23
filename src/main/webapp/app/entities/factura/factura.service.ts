import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFactura } from 'app/shared/model/factura.model';

type EntityResponseType = HttpResponse<IFactura>;
type EntityArrayResponseType = HttpResponse<IFactura[]>;

@Injectable({ providedIn: 'root' })
export class FacturaService {
  public resourceUrl = SERVER_API_URL + 'api/facturas';

  constructor(protected http: HttpClient) {}

  create(factura: IFactura): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(factura);
    return this.http
      .post<IFactura>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(factura: IFactura): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(factura);
    return this.http
      .put<IFactura>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFactura>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFactura[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(factura: IFactura): IFactura {
    const copy: IFactura = Object.assign({}, factura, {
      vencimiento1: factura.vencimiento1 && factura.vencimiento1.isValid() ? factura.vencimiento1.format(DATE_FORMAT) : undefined,
      vencimiento2: factura.vencimiento2 && factura.vencimiento2.isValid() ? factura.vencimiento2.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.vencimiento1 = res.body.vencimiento1 ? moment(res.body.vencimiento1) : undefined;
      res.body.vencimiento2 = res.body.vencimiento2 ? moment(res.body.vencimiento2) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((factura: IFactura) => {
        factura.vencimiento1 = factura.vencimiento1 ? moment(factura.vencimiento1) : undefined;
        factura.vencimiento2 = factura.vencimiento2 ? moment(factura.vencimiento2) : undefined;
      });
    }
    return res;
  }
}
