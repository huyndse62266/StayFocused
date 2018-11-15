import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVoucherLog } from 'app/shared/model/voucher-log.model';

@Component({
  selector: '-voucher-log-detail',
  templateUrl: './voucher-log-detail.component.html'
})
export class VoucherLogDetailComponent implements OnInit {
  voucherLog: IVoucherLog;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ voucherLog }) => {
      this.voucherLog = voucherLog;
    });
  }

  previousState() {
    window.history.back();
  }
}
