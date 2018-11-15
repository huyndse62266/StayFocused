import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStoreGroup } from 'app/shared/model/store-group.model';
import { Principal } from 'app/core';
import { StoreGroupService } from './store-group.service';

@Component({
  selector: '-store-group',
  templateUrl: './store-group.component.html'
})
export class StoreGroupComponent implements OnInit, OnDestroy {
  storeGroups: IStoreGroup[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private storeGroupService: StoreGroupService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.storeGroupService.query().subscribe(
      (res: HttpResponse<IStoreGroup[]>) => {
        this.storeGroups = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInStoreGroups();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStoreGroup) {
    return item.id;
  }

  registerChangeInStoreGroups() {
    this.eventSubscriber = this.eventManager.subscribe('storeGroupListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
