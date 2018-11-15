import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStore } from 'app/shared/model/store.model';
import { StoreService } from './store.service';

@Component({
  selector: '-store-delete-dialog',
  templateUrl: './store-delete-dialog.component.html'
})
export class StoreDeleteDialogComponent {
  store: IStore;

  constructor(private storeService: StoreService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.storeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'storeListModification',
        content: 'Deleted an store'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: '-store-delete-popup',
  template: ''
})
export class StoreDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ store }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StoreDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.store = store;
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
