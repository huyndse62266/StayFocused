import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActivitiesLog } from 'app/shared/model/activities-log.model';
import { ActivitiesLogService } from './activities-log.service';

@Component({
  selector: '-activities-log-delete-dialog',
  templateUrl: './activities-log-delete-dialog.component.html'
})
export class ActivitiesLogDeleteDialogComponent {
  activitiesLog: IActivitiesLog;

  constructor(
    private activitiesLogService: ActivitiesLogService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.activitiesLogService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'activitiesLogListModification',
        content: 'Deleted an activitiesLog'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: '-activities-log-delete-popup',
  template: ''
})
export class ActivitiesLogDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ activitiesLog }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ActivitiesLogDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.activitiesLog = activitiesLog;
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
