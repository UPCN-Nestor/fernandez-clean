import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IArchivoFacturas } from 'app/shared/model/archivo-facturas.model';

type EntityResponseType = HttpResponse<IArchivoFacturas>;
type EntityArrayResponseType = HttpResponse<IArchivoFacturas[]>;

@Injectable({ providedIn: 'root' })
export class ArchivoFacturasService {
  public resourceUrl = SERVER_API_URL + 'api/archivo-facturas';

  constructor(protected http: HttpClient) {}

  create(archivoFacturas: IArchivoFacturas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(archivoFacturas);
    return this.http
      .post<IArchivoFacturas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(archivoFacturas: IArchivoFacturas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(archivoFacturas);
    return this.http
      .put<IArchivoFacturas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IArchivoFacturas>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IArchivoFacturas[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(archivoFacturas: IArchivoFacturas): IArchivoFacturas {
    const copy: IArchivoFacturas = Object.assign({}, archivoFacturas, {
      fecha: archivoFacturas.fecha && archivoFacturas.fecha.isValid() ? archivoFacturas.fecha.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((archivoFacturas: IArchivoFacturas) => {
        archivoFacturas.fecha = archivoFacturas.fecha ? moment(archivoFacturas.fecha) : undefined;
      });
    }
    return res;
  }
}
