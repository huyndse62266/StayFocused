import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPost } from 'app/shared/model/post.model';
import { PostService } from './post.service';
import { IStore } from 'app/shared/model/store.model';
import { StoreService } from 'app/entities/store';

@Component({
  selector: '-post-update',
  templateUrl: './post-update.component.html'
})
export class PostUpdateComponent implements OnInit {
  private _post: IPost;
  isSaving: boolean;

  stores: IStore[];
  postStartDate: string;
  postEndDate: string;

  constructor(
    private jhiAlertService: JhiAlertService,
    private postService: PostService,
    private storeService: StoreService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ post }) => {
      this.post = post;
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
    this.post.postStartDate = moment(this.postStartDate, DATE_TIME_FORMAT);
    this.post.postEndDate = moment(this.postEndDate, DATE_TIME_FORMAT);
    if (this.post.id !== undefined) {
      this.subscribeToSaveResponse(this.postService.update(this.post));
    } else {
      this.subscribeToSaveResponse(this.postService.create(this.post));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IPost>>) {
    result.subscribe((res: HttpResponse<IPost>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
  get post() {
    return this._post;
  }

  set post(post: IPost) {
    this._post = post;
    this.postStartDate = moment(post.postStartDate).format(DATE_TIME_FORMAT);
    this.postEndDate = moment(post.postEndDate).format(DATE_TIME_FORMAT);
  }
}
