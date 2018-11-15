import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserStoreGroup } from 'app/shared/model/user-store-group.model';

type EntityResponseType = HttpResponse<IUserStoreGroup>;
type EntityArrayResponseType = HttpResponse<IUserStoreGroup[]>;

@Injectable({ providedIn: 'root' })
export class UserStoreGroupService {
  public resourceUrl = SERVER_API_URL + 'api/user-store-groups';

  constructor(private http: HttpClient) {}

  create(userStoreGroup: IUserStoreGroup): Observable<EntityResponseType> {
    return this.http.post<IUserStoreGroup>(this.resourceUrl, userStoreGroup, { observe: 'response' });
  }

  update(userStoreGroup: IUserStoreGroup): Observable<EntityResponseType> {
    return this.http.put<IUserStoreGroup>(this.resourceUrl, userStoreGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserStoreGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserStoreGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
