import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IListStore } from 'app/shared/model/list-store.model';

type EntityResponseType = HttpResponse<IListStore>;
type EntityArrayResponseType = HttpResponse<IListStore[]>;

@Injectable({ providedIn: 'root' })
export class ListStoreService {
  private resourceUrl = SERVER_API_URL + 'api/list-stores';

  constructor(private http: HttpClient) {}

  create(listStore: IListStore): Observable<EntityResponseType> {
    return this.http.post<IListStore>(this.resourceUrl, listStore, { observe: 'response' });
  }

  update(listStore: IListStore): Observable<EntityResponseType> {
    return this.http.put<IListStore>(this.resourceUrl, listStore, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IListStore>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IListStore[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
