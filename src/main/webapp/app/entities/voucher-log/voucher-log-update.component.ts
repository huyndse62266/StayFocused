import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IVoucherLog } from 'app/shared/model/voucher-log.model';
import { VoucherLogService } from './voucher-log.service';
import { IUser, UserService } from 'app/core';
import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from 'app/entities/voucher';

@Component({
  selector: '-voucher-log-update',
  templateUrl: './voucher-log-update.component.html'
})
export class VoucherLogUpdateComponent implements OnInit {
  private _voucherLog: IVoucherLog;
  isSaving: boolean;

  users: IUser[];

  vouchers: IVoucher[];
  voucherLogDateUsed: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private voucherLogService: VoucherLogService,
    private userService: UserService,
    private voucherService: VoucherService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ voucherLog }) => {
      this.voucherLog = voucherLog;
    });
    this.userService.query().subscribe(
      (res: HttpResponse<IUser[]>) => {
        this.users = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.voucherService.query().subscribe(
      (res: HttpResponse<IVoucher[]>) => {
        this.vouchers = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.voucherLog.voucherLogDateUsed = moment(this.voucherLogDateUsed, DATE_TIME_FORMAT);
    if (this.voucherLog.id !== undefined) {
      this.subscribeToSaveResponse(this.voucherLogService.update(this.voucherLog));
    } else {
      this.subscribeToSaveResponse(this.voucherLogService.create(this.voucherLog));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IVoucherLog>>) {
    result.subscribe((res: HttpResponse<IVoucherLog>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackVoucherById(index: number, item: IVoucher) {
    return item.id;
  }
  get voucherLog() {
    return this._voucherLog;
  }

  set voucherLog(voucherLog: IVoucherLog) {
    this._voucherLog = voucherLog;
    this.voucherLogDateUsed = moment(voucherLog.voucherLogDateUsed).format(DATE_TIME_FORMAT);
  }
}
