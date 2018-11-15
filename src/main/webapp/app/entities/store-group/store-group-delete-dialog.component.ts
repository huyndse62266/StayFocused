import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStoreGroup } from 'app/shared/model/store-group.model';
import { StoreGroupService } from './store-group.service';

@Component({
  selector: '-store-group-delete-dialog',
  templateUrl: './store-group-delete-dialog.component.html'
})
export class StoreGroupDeleteDialogComponent {
  storeGroup: IStoreGroup;

  constructor(private storeGroupService: StoreGroupService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.storeGroupService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'storeGroupListModification',
        content: 'Deleted an storeGroup'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: '-store-group-delete-popup',
  template: ''
})
export class StoreGroupDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ storeGroup }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StoreGroupDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.storeGroup = storeGroup;
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
