import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserStoreGroup } from 'app/shared/model/user-store-group.model';

@Component({
  selector: '-user-store-group-detail',
  templateUrl: './user-store-group-detail.component.html'
})
export class UserStoreGroupDetailComponent implements OnInit {
  userStoreGroup: IUserStoreGroup;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userStoreGroup }) => {
      this.userStoreGroup = userStoreGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}
