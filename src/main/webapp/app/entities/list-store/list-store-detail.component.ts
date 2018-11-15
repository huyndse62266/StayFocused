import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IListStore } from 'app/shared/model/list-store.model';

@Component({
  selector: '-list-store-detail',
  templateUrl: './list-store-detail.component.html'
})
export class ListStoreDetailComponent implements OnInit {
  listStore: IListStore;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ listStore }) => {
      this.listStore = listStore;
    });
  }

  previousState() {
    window.history.back();
  }
}
