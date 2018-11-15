import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IListStore } from 'app/shared/model/list-store.model';
import { ListStoreService } from './list-store.service';
import { IStore } from 'app/shared/model/store.model';
import { StoreService } from 'app/entities/store';

@Component({
  selector: '-list-store-update',
  templateUrl: './list-store-update.component.html'
})
export class ListStoreUpdateComponent implements OnInit {
  private _listStore: IListStore;
  isSaving: boolean;

  stores: IStore[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private listStoreService: ListStoreService,
    private storeService: StoreService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ listStore }) => {
      this.listStore = listStore;
    });
    this.storeService.query().subscribe(
      (res: HttpResponse<IStore[]>) => {
        this.stores = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.listStore.id !== undefined) {
      this.subscribeToSaveResponse(this.listStoreService.update(this.listStore));
    } else {
      this.subscribeToSaveResponse(this.listStoreService.create(this.listStore));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IListStore>>) {
    result.subscribe((res: HttpResponse<IListStore>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackStoreById(index: number, item: IStore) {
    return item.id;
  }
  get listStore() {
    return this._listStore;
  }

  set listStore(listStore: IListStore) {
    this._listStore = listStore;
  }
}
