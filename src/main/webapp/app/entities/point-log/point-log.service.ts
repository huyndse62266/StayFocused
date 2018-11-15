import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPointLog } from 'app/shared/model/point-log.model';

type EntityResponseType = HttpResponse<IPointLog>;
type EntityArrayResponseType = HttpResponse<IPointLog[]>;

@Injectable({ providedIn: 'root' })
export class PointLogService {
  private resourceUrl = SERVER_API_URL + 'api/point-logs';

  constructor(private http: HttpClient) {}

  create(pointLog: IPointLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointLog);
    return this.http
      .post<IPointLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pointLog: IPointLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointLog);
    return this.http
      .put<IPointLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPointLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPointLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  private convertDateFromClient(pointLog: IPointLog): IPointLog {
    const copy: IPointLog = Object.assign({}, pointLog, {
      pointLogDateUsed: pointLog.pointLogDateUsed != null && pointLog.pointLogDateUsed.isValid() ? pointLog.pointLogDateUsed.toJSON() : null
    });
    return copy;
  }

  private convertDateFromServer(res: EntityResponseType): EntityResponseType {
    res.body.pointLogDateUsed = res.body.pointLogDateUsed != null ? moment(res.body.pointLogDateUsed) : null;
    return res;
  }

  private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    res.body.forEach((pointLog: IPointLog) => {
      pointLog.pointLogDateUsed = pointLog.pointLogDateUsed != null ? moment(pointLog.pointLogDateUsed) : null;
    });
    return res;
  }
}
