import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStoreType } from 'app/shared/model/store-type.model';

@Component({
  selector: '-store-type-detail',
  templateUrl: './store-type-detail.component.html'
})
export class StoreTypeDetailComponent implements OnInit {
  storeType: IStoreType;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ storeType }) => {
      this.storeType = storeType;
    });
  }

  previousState() {
    window.history.back();
  }
}
