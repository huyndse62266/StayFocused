import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStore } from 'app/shared/model/store.model';

@Component({
  selector: '-store-detail',
  templateUrl: './store-detail.component.html'
})
export class StoreDetailComponent implements OnInit {
  store: IStore;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ store }) => {
      this.store = store;
    });
  }

  previousState() {
    window.history.back();
  }
}
