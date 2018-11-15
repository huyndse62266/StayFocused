import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVoucherLog } from 'app/shared/model/voucher-log.model';
import { Principal } from 'app/core';
import { VoucherLogService } from './voucher-log.service';

@Component({
  selector: '-voucher-log',
  templateUrl: './voucher-log.component.html'
})
export class VoucherLogComponent implements OnInit, OnDestroy {
  voucherLogs: IVoucherLog[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private voucherLogService: VoucherLogService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.voucherLogService.query().subscribe(
      (res: HttpResponse<IVoucherLog[]>) => {
        this.voucherLogs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInVoucherLogs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVoucherLog) {
    return item.id;
  }

  registerChangeInVoucherLogs() {
    this.eventSubscriber = this.eventManager.subscribe('voucherLogListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
