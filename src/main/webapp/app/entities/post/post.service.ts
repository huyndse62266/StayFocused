import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPost } from 'app/shared/model/post.model';

type EntityResponseType = HttpResponse<IPost>;
type EntityArrayResponseType = HttpResponse<IPost[]>;

@Injectable({ providedIn: 'root' })
export class PostService {
  private resourceUrl = SERVER_API_URL + 'api/posts';

  constructor(private http: HttpClient) {}

  create(post: IPost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(post);
    return this.http
      .post<IPost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(post: IPost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(post);
    return this.http
      .put<IPost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPost>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPost[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  private convertDateFromClient(post: IPost): IPost {
    const copy: IPost = Object.assign({}, post, {
      postStartDate: post.postStartDate != null && post.postStartDate.isValid() ? post.postStartDate.toJSON() : null,
      postEndDate: post.postEndDate != null && post.postEndDate.isValid() ? post.postEndDate.toJSON() : null
    });
    return copy;
  }

  private convertDateFromServer(res: EntityResponseType): EntityResponseType {
    res.body.postStartDate = res.body.postStartDate != null ? moment(res.body.postStartDate) : null;
    res.body.postEndDate = res.body.postEndDate != null ? moment(res.body.postEndDate) : null;
    return res;
  }

  private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    res.body.forEach((post: IPost) => {
      post.postStartDate = post.postStartDate != null ? moment(post.postStartDate) : null;
      post.postEndDate = post.postEndDate != null ? moment(post.postEndDate) : null;
    });
    return res;
  }
}
