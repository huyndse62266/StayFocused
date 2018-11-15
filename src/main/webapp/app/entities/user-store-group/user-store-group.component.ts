import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserStoreGroup } from 'app/shared/model/user-store-group.model';
import { Principal } from 'app/core';
import { UserStoreGroupService } from './user-store-group.service';

@Component({
  selector: '-user-store-group',
  templateUrl: './user-store-group.component.html'
})
export class UserStoreGroupComponent implements OnInit, OnDestroy {
  userStoreGroups: IUserStoreGroup[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private userStoreGroupService: UserStoreGroupService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.userStoreGroupService.query().subscribe(
      (res: HttpResponse<IUserStoreGroup[]>) => {
        this.userStoreGroups = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUserStoreGroups();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUserStoreGroup) {
    return item.id;
  }

  registerChangeInUserStoreGroups() {
    this.eventSubscriber = this.eventManager.subscribe('userStoreGroupListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
