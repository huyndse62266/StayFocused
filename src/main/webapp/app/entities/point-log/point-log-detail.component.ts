import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPointLog } from 'app/shared/model/point-log.model';

@Component({
  selector: '-point-log-detail',
  templateUrl: './point-log-detail.component.html'
})
export class PointLogDetailComponent implements OnInit {
  pointLog: IPointLog;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pointLog }) => {
      this.pointLog = pointLog;
    });
  }

  previousState() {
    window.history.back();
  }
}
