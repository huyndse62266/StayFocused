import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStoreGroup } from 'app/shared/model/store-group.model';

@Component({
  selector: '-store-group-detail',
  templateUrl: './store-group-detail.component.html'
})
export class StoreGroupDetailComponent implements OnInit {
  storeGroup: IStoreGroup;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ storeGroup }) => {
      this.storeGroup = storeGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}
