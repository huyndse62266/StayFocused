import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActivitiesLog } from 'app/shared/model/activities-log.model';

@Component({
  selector: '-activities-log-detail',
  templateUrl: './activities-log-detail.component.html'
})
export class ActivitiesLogDetailComponent implements OnInit {
  activitiesLog: IActivitiesLog;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ activitiesLog }) => {
      this.activitiesLog = activitiesLog;
    });
  }

  previousState() {
    window.history.back();
  }
}
