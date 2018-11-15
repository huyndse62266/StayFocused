import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IActivitiesLog } from 'app/shared/model/activities-log.model';
import { ActivitiesLogService } from './activities-log.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: '-activities-log-update',
  templateUrl: './activities-log-update.component.html'
})
export class ActivitiesLogUpdateComponent implements OnInit {
  private _activitiesLog: IActivitiesLog;
  isSaving: boolean;

  users: IUser[];
  activitiesLogDate: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private activitiesLogService: ActivitiesLogService,
    private userService: UserService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ activitiesLog }) => {
      this.activitiesLog = activitiesLog;
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
    this.activitiesLog.activitiesLogDate = moment(this.activitiesLogDate, DATE_TIME_FORMAT);
    if (this.activitiesLog.id !== undefined) {
      this.subscribeToSaveResponse(this.activitiesLogService.update(this.activitiesLog));
    } else {
      this.subscribeToSaveResponse(this.activitiesLogService.create(this.activitiesLog));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IActivitiesLog>>) {
    result.subscribe((res: HttpResponse<IActivitiesLog>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
  get activitiesLog() {
    return this._activitiesLog;
  }

  set activitiesLog(activitiesLog: IActivitiesLog) {
    this._activitiesLog = activitiesLog;
    this.activitiesLogDate = moment(activitiesLog.activitiesLogDate).format(DATE_TIME_FORMAT);
  }
}
