import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPointLog } from 'app/shared/model/point-log.model';
import { Principal } from 'app/core';
import { PointLogService } from './point-log.service';

@Component({
  selector: '-point-log',
  templateUrl: './point-log.component.html'
})
export class PointLogComponent implements OnInit, OnDestroy {
  pointLogs: IPointLog[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private pointLogService: PointLogService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.pointLogService.query().subscribe(
      (res: HttpResponse<IPointLog[]>) => {
        this.pointLogs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPointLogs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPointLog) {
    return item.id;
  }

  registerChangeInPointLogs() {
    this.eventSubscriber = this.eventManager.subscribe('pointLogListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
