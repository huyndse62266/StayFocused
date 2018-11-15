import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IActivitiesLog } from 'app/shared/model/activities-log.model';
import { Principal } from 'app/core';
import { ActivitiesLogService } from './activities-log.service';

@Component({
  selector: '-activities-log',
  templateUrl: './activities-log.component.html'
})
export class ActivitiesLogComponent implements OnInit, OnDestroy {
  activitiesLogs: IActivitiesLog[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private activitiesLogService: ActivitiesLogService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.activitiesLogService.query().subscribe(
      (res: HttpResponse<IActivitiesLog[]>) => {
        this.activitiesLogs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInActivitiesLogs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IActivitiesLog) {
    return item.id;
  }

  registerChangeInActivitiesLogs() {
    this.eventSubscriber = this.eventManager.subscribe('activitiesLogListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
