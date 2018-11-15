import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVoucher } from 'app/shared/model/voucher.model';

@Component({
  selector: '-voucher-detail',
  templateUrl: './voucher-detail.component.html'
})
export class VoucherDetailComponent implements OnInit {
  voucher: IVoucher;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ voucher }) => {
      this.voucher = voucher;
    });
  }

  previousState() {
    window.history.back();
  }
}
