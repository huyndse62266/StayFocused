import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStore } from 'app/shared/model/store.model';
import { Principal } from 'app/core';
import { StoreService } from './store.service';

@Component({
  selector: '-store',
  templateUrl: './store.component.html'
})
export class StoreComponent implements OnInit, OnDestroy {
  stores: IStore[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private storeService: StoreService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.storeService.query().subscribe(
      (res: HttpResponse<IStore[]>) => {
        this.stores = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInStores();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStore) {
    return item.id;
  }

  registerChangeInStores() {
    this.eventSubscriber = this.eventManager.subscribe('storeListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
