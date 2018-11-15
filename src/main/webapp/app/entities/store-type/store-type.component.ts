import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStoreType } from 'app/shared/model/store-type.model';
import { Principal } from 'app/core';
import { StoreTypeService } from './store-type.service';

@Component({
  selector: '-store-type',
  templateUrl: './store-type.component.html'
})
export class StoreTypeComponent implements OnInit, OnDestroy {
  storeTypes: IStoreType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private storeTypeService: StoreTypeService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.storeTypeService.query().subscribe(
      (res: HttpResponse<IStoreType[]>) => {
        this.storeTypes = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInStoreTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStoreType) {
    return item.id;
  }

  registerChangeInStoreTypes() {
    this.eventSubscriber = this.eventManager.subscribe('storeTypeListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
