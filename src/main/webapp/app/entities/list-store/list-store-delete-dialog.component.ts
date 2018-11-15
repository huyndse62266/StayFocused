import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IListStore } from 'app/shared/model/list-store.model';
import { ListStoreService } from './list-store.service';

@Component({
  selector: '-list-store-delete-dialog',
  templateUrl: './list-store-delete-dialog.component.html'
})
export class ListStoreDeleteDialogComponent {
  listStore: IListStore;

  constructor(private listStoreService: ListStoreService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.listStoreService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'listStoreListModification',
        content: 'Deleted an listStore'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: '-list-store-delete-popup',
  template: ''
})
export class ListStoreDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ listStore }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ListStoreDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.listStore = listStore;
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
