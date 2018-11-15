import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IActivitiesLog } from 'app/shared/model/activities-log.model';

type EntityResponseType = HttpResponse<IActivitiesLog>;
type EntityArrayResponseType = HttpResponse<IActivitiesLog[]>;

@Injectable({ providedIn: 'root' })
export class ActivitiesLogService {
  private resourceUrl = SERVER_API_URL + 'api/activities-logs';

  constructor(private http: HttpClient) {}

  create(activitiesLog: IActivitiesLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(activitiesLog);
    return this.http
      .post<IActivitiesLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(activitiesLog: IActivitiesLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(activitiesLog);
    return this.http
      .put<IActivitiesLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IActivitiesLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IActivitiesLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  private convertDateFromClient(activitiesLog: IActivitiesLog): IActivitiesLog {
    const copy: IActivitiesLog = Object.assign({}, activitiesLog, {
      activitiesLogDate:
        activitiesLog.activitiesLogDate != null && activitiesLog.activitiesLogDate.isValid()
          ? activitiesLog.activitiesLogDate.toJSON()
          : null
    });
    return copy;
  }

  private convertDateFromServer(res: EntityResponseType): EntityResponseType {
    res.body.activitiesLogDate = res.body.activitiesLogDate != null ? moment(res.body.activitiesLogDate) : null;
    return res;
  }

  private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    res.body.forEach((activitiesLog: IActivitiesLog) => {
      activitiesLog.activitiesLogDate = activitiesLog.activitiesLogDate != null ? moment(activitiesLog.activitiesLogDate) : null;
    });
    return res;
  }
}
