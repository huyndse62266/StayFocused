import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPointLog } from 'app/shared/model/point-log.model';
import { PointLogService } from './point-log.service';

@Component({
  selector: '-point-log-delete-dialog',
  templateUrl: './point-log-delete-dialog.component.html'
})
export class PointLogDeleteDialogComponent {
  pointLog: IPointLog;

  constructor(private pointLogService: PointLogService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pointLogService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pointLogListModification',
        content: 'Deleted an pointLog'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: '-point-log-delete-popup',
  template: ''
})
export class PointLogDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pointLog }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PointLogDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pointLog = pointLog;
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
