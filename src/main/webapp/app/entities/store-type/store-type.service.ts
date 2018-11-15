import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStoreType } from 'app/shared/model/store-type.model';

type EntityResponseType = HttpResponse<IStoreType>;
type EntityArrayResponseType = HttpResponse<IStoreType[]>;

@Injectable({ providedIn: 'root' })
export class StoreTypeService {
  private resourceUrl = SERVER_API_URL + 'api/store-types';

  constructor(private http: HttpClient) {}

  create(storeType: IStoreType): Observable<EntityResponseType> {
    return this.http.post<IStoreType>(this.resourceUrl, storeType, { observe: 'response' });
  }

  update(storeType: IStoreType): Observable<EntityResponseType> {
    return this.http.put<IStoreType>(this.resourceUrl, storeType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStoreType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStoreType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
