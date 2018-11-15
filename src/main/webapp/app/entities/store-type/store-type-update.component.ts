import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IStoreType } from 'app/shared/model/store-type.model';
import { StoreTypeService } from './store-type.service';

@Component({
  selector: '-store-type-update',
  templateUrl: './store-type-update.component.html'
})
export class StoreTypeUpdateComponent implements OnInit {
  private _storeType: IStoreType;
  isSaving: boolean;

  constructor(private storeTypeService: StoreTypeService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ storeType }) => {
      this.storeType = storeType;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.storeType.id !== undefined) {
      this.subscribeToSaveResponse(this.storeTypeService.update(this.storeType));
    } else {
      this.subscribeToSaveResponse(this.storeTypeService.create(this.storeType));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IStoreType>>) {
    result.subscribe((res: HttpResponse<IStoreType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
  get storeType() {
    return this._storeType;
  }

  set storeType(storeType: IStoreType) {
    this._storeType = storeType;
  }
}
