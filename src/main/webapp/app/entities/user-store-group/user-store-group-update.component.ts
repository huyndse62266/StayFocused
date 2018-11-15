import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUserStoreGroup } from 'app/shared/model/user-store-group.model';
import { UserStoreGroupService } from './user-store-group.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: '-user-store-group-update',
  templateUrl: './user-store-group-update.component.html'
})
export class UserStoreGroupUpdateComponent implements OnInit {
  userStoreGroup: IUserStoreGroup;
  isSaving: boolean;

  users: IUser[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private userStoreGroupService: UserStoreGroupService,
    private userService: UserService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userStoreGroup }) => {
      this.userStoreGroup = userStoreGroup;
    });
    this.userService.query().subscribe(
      (res: HttpResponse<IUser[]>) => {
        this.users = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.userStoreGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.userStoreGroupService.update(this.userStoreGroup));
    } else {
      this.subscribeToSaveResponse(this.userStoreGroupService.create(this.userStoreGroup));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IUserStoreGroup>>) {
    result.subscribe((res: HttpResponse<IUserStoreGroup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
