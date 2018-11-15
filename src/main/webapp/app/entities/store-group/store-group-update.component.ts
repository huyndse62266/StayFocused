import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IStoreGroup } from 'app/shared/model/store-group.model';
import { StoreGroupService } from './store-group.service';
import { IStoreType } from 'app/shared/model/store-type.model';
import { StoreTypeService } from 'app/entities/store-type';

@Component({
  selector: '-store-group-update',
  templateUrl: './store-group-update.component.html'
})
export class StoreGroupUpdateComponent implements OnInit {
  private _storeGroup: IStoreGroup;
  isSaving: boolean;

  storetypes: IStoreType[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private storeGroupService: StoreGroupService,
    private storeTypeService: StoreTypeService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ storeGroup }) => {
      this.storeGroup = storeGroup;
    });
    this.storeTypeService.query().subscribe(
      (res: HttpResponse<IStoreType[]>) => {
        this.storetypes = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.storeGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.storeGroupService.update(this.storeGroup));
    } else {
      this.subscribeToSaveResponse(this.storeGroupService.create(this.storeGroup));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IStoreGroup>>) {
    result.subscribe((res: HttpResponse<IStoreGroup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackStoreTypeById(index: number, item: IStoreType) {
    return item.id;
  }
  get storeGroup() {
    return this._storeGroup;
  }

  set storeGroup(storeGroup: IStoreGroup) {
    this._storeGroup = storeGroup;
  }
}
