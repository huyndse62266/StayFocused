import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IStore } from 'app/shared/model/store.model';
import { StoreService } from './store.service';

@Component({
  selector: '-store-update',
  templateUrl: './store-update.component.html'
})
export class StoreUpdateComponent implements OnInit {
  private _store: IStore;
  isSaving: boolean;

  constructor(private storeService: StoreService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ store }) => {
      this.store = store;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.store.id !== undefined) {
      this.subscribeToSaveResponse(this.storeService.update(this.store));
    } else {
      this.subscribeToSaveResponse(this.storeService.create(this.store));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IStore>>) {
    result.subscribe((res: HttpResponse<IStore>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
  get store() {
    return this._store;
  }

  set store(store: IStore) {
    this._store = store;
  }
}
