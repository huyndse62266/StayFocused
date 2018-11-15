import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from './voucher.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post';

@Component({
  selector: '-voucher-update',
  templateUrl: './voucher-update.component.html'
})
export class VoucherUpdateComponent implements OnInit {
  private _voucher: IVoucher;
  isSaving: boolean;

  posts: IPost[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private voucherService: VoucherService,
    private postService: PostService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ voucher }) => {
      this.voucher = voucher;
    });
    this.postService.query().subscribe(
      (res: HttpResponse<IPost[]>) => {
        this.posts = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.voucher.id !== undefined) {
      this.subscribeToSaveResponse(this.voucherService.update(this.voucher));
    } else {
      this.subscribeToSaveResponse(this.voucherService.create(this.voucher));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IVoucher>>) {
    result.subscribe((res: HttpResponse<IVoucher>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackPostById(index: number, item: IPost) {
    return item.id;
  }
  get voucher() {
    return this._voucher;
  }

  set voucher(voucher: IVoucher) {
    this._voucher = voucher;
  }
}
