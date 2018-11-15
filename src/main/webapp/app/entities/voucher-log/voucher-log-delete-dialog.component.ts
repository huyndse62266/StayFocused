import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVoucherLog } from 'app/shared/model/voucher-log.model';
import { VoucherLogService } from './voucher-log.service';

@Component({
  selector: '-voucher-log-delete-dialog',
  templateUrl: './voucher-log-delete-dialog.component.html'
})
export class VoucherLogDeleteDialogComponent {
  voucherLog: IVoucherLog;

  constructor(private voucherLogService: VoucherLogService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.voucherLogService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'voucherLogListModification',
        content: 'Deleted an voucherLog'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: '-voucher-log-delete-popup',
  template: ''
})
export class VoucherLogDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ voucherLog }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VoucherLogDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.voucherLog = voucherLog;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
