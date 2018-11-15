import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserStoreGroup } from 'app/shared/model/user-store-group.model';
import { UserStoreGroupService } from './user-store-group.service';

@Component({
  selector: '-user-store-group-delete-dialog',
  templateUrl: './user-store-group-delete-dialog.component.html'
})
export class UserStoreGroupDeleteDialogComponent {
  userStoreGroup: IUserStoreGroup;

  constructor(
    private userStoreGroupService: UserStoreGroupService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.userStoreGroupService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'userStoreGroupListModification',
        content: 'Deleted an userStoreGroup'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: '-user-store-group-delete-popup',
  template: ''
})
export class UserStoreGroupDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userStoreGroup }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserStoreGroupDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.userStoreGroup = userStoreGroup;
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
