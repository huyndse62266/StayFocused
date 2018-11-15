import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPointLog } from 'app/shared/model/point-log.model';
import { PointLogService } from './point-log.service';
import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from 'app/entities/voucher';
import { IUser, UserService } from 'app/core';

@Component({
  selector: '-point-log-update',
  templateUrl: './point-log-update.component.html'
})
export class PointLogUpdateComponent implements OnInit {
  private _pointLog: IPointLog;
  isSaving: boolean;

  vouchers: IVoucher[];

  users: IUser[];
  pointLogDateUsed: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private pointLogService: PointLogService,
    private voucherService: VoucherService,
    private userService: UserService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pointLog }) => {
      this.pointLog = pointLog;
    });
    this.voucherService.query({ filter: 'pointlog-is-null' }).subscribe(
      (res: HttpResponse<IVoucher[]>) => {
        if (!this.pointLog.voucherId) {
          this.vouchers = res.body;
        } else {
          this.voucherService.find(this.pointLog.voucherId).subscribe(
            (subRes: HttpResponse<IVoucher>) => {
              this.vouchers = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.userService.query().subscribe(
      (res: HttpResponse<IUser[]>) => {
        this.users = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.pointLog.pointLogDateUsed = moment(this.pointLogDateUsed, DATE_TIME_FORMAT);
    if (this.pointLog.id !== undefined) {
      this.subscribeToSaveResponse(this.pointLogService.update(this.pointLog));
    } else {
      this.subscribeToSaveResponse(this.pointLogService.create(this.pointLog));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IPointLog>>) {
    result.subscribe((res: HttpResponse<IPointLog>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackVoucherById(index: number, item: IVoucher) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
  get pointLog() {
    return this._pointLog;
  }

  set pointLog(pointLog: IPointLog) {
    this._pointLog = pointLog;
    this.pointLogDateUsed = moment(pointLog.pointLogDateUsed).format(DATE_TIME_FORMAT);
  }
}
