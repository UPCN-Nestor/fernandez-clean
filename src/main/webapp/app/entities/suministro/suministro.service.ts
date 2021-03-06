import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISuministro } from 'app/shared/model/suministro.model';

type EntityResponseType = HttpResponse<ISuministro>;
type EntityArrayResponseType = HttpResponse<ISuministro[]>;

@Injectable({ providedIn: 'root' })
export class SuministroService {
  public resourceUrl = SERVER_API_URL + 'api/suministros';

  constructor(protected http: HttpClient) {}

  create(suministro: ISuministro): Observable<EntityResponseType> {
    return this.http.post<ISuministro>(this.resourceUrl, suministro, { observe: 'response' });
  }

  createConNumeroYDNI(suministro: string, dni: string): Observable<EntityResponseType> {
    return this.http.get<ISuministro>(this.resourceUrl + '/n/' + suministro + '/' + dni, { observe: 'response' });
  }

  update(suministro: ISuministro): Observable<EntityResponseType> {
    return this.http.put<ISuministro>(this.resourceUrl, suministro, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISuministro>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISuministro[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  allByLoggedUser(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISuministro[]>(this.resourceUrl + '/u', { params: options, observe: 'response' });
  }
}
