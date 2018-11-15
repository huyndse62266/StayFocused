import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from './voucher.service';

@Component({
  selector: '-voucher-delete-dialog',
  templateUrl: './voucher-delete-dialog.component.html'
})
export class VoucherDeleteDialogComponent {
  voucher: IVoucher;

  constructor(private voucherService: VoucherService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.voucherService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'voucherListModification',
        content: 'Deleted an voucher'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: '-voucher-delete-popup',
  template: ''
})
export class VoucherDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ voucher }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VoucherDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.voucher = voucher;
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
