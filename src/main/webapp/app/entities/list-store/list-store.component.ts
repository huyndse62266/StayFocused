import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IListStore } from 'app/shared/model/list-store.model';
import { Principal } from 'app/core';
import { ListStoreService } from './list-store.service';

@Component({
  selector: '-list-store',
  templateUrl: './list-store.component.html'
})
export class ListStoreComponent implements OnInit, OnDestroy {
  listStores: IListStore[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private listStoreService: ListStoreService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.listStoreService.query().subscribe(
      (res: HttpResponse<IListStore[]>) => {
        this.listStores = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInListStores();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IListStore) {
    return item.id;
  }

  registerChangeInListStores() {
    this.eventSubscriber = this.eventManager.subscribe('listStoreListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
