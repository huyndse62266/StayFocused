import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVoucherLog } from 'app/shared/model/voucher-log.model';

type EntityResponseType = HttpResponse<IVoucherLog>;
type EntityArrayResponseType = HttpResponse<IVoucherLog[]>;

@Injectable({ providedIn: 'root' })
export class VoucherLogService {
  private resourceUrl = SERVER_API_URL + 'api/voucher-logs';

  constructor(private http: HttpClient) {}

  create(voucherLog: IVoucherLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voucherLog);
    return this.http
      .post<IVoucherLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(voucherLog: IVoucherLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voucherLog);
    return this.http
      .put<IVoucherLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVoucherLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVoucherLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  private convertDateFromClient(voucherLog: IVoucherLog): IVoucherLog {
    const copy: IVoucherLog = Object.assign({}, voucherLog, {
      voucherLogDateUsed:
        voucherLog.voucherLogDateUsed != null && voucherLog.voucherLogDateUsed.isValid() ? voucherLog.voucherLogDateUsed.toJSON() : null
    });
    return copy;
  }

  private convertDateFromServer(res: EntityResponseType): EntityResponseType {
    res.body.voucherLogDateUsed = res.body.voucherLogDateUsed != null ? moment(res.body.voucherLogDateUsed) : null;
    return res;
  }

  private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    res.body.forEach((voucherLog: IVoucherLog) => {
      voucherLog.voucherLogDateUsed = voucherLog.voucherLogDateUsed != null ? moment(voucherLog.voucherLogDateUsed) : null;
    });
    return res;
  }
}
