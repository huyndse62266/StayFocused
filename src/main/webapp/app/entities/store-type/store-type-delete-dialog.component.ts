import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStoreType } from 'app/shared/model/store-type.model';
import { StoreTypeService } from './store-type.service';

@Component({
  selector: '-store-type-delete-dialog',
  templateUrl: './store-type-delete-dialog.component.html'
})
export class StoreTypeDeleteDialogComponent {
  storeType: IStoreType;

  constructor(private storeTypeService: StoreTypeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.storeTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'storeTypeListModification',
        content: 'Deleted an storeType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: '-store-type-delete-popup',
  template: ''
})
export class StoreTypeDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ storeType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StoreTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.storeType = storeType;
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
