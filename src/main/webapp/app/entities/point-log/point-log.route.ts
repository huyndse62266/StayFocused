import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PointLog } from 'app/shared/model/point-log.model';
import { PointLogService } from './point-log.service';
import { PointLogComponent } from './point-log.component';
import { PointLogDetailComponent } from './point-log-detail.component';
import { PointLogUpdateComponent } from './point-log-update.component';
import { PointLogDeletePopupComponent } from './point-log-delete-dialog.component';
import { IPointLog } from 'app/shared/model/point-log.model';

@Injectable({ providedIn: 'root' })
export class PointLogResolve implements Resolve<IPointLog> {
  constructor(private service: PointLogService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((pointLog: HttpResponse<PointLog>) => pointLog.body));
    }
    return of(new PointLog());
  }
}

export const pointLogRoute: Routes = [
  {
    path: 'point-log',
    component: PointLogComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PointLogs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'point-log/:id/view',
    component: PointLogDetailComponent,
    resolve: {
      pointLog: PointLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PointLogs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'point-log/new',
    component: PointLogUpdateComponent,
    resolve: {
      pointLog: PointLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PointLogs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'point-log/:id/edit',
    component: PointLogUpdateComponent,
    resolve: {
      pointLog: PointLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PointLogs'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pointLogPopupRoute: Routes = [
  {
    path: 'point-log/:id/delete',
    component: PointLogDeletePopupComponent,
    resolve: {
      pointLog: PointLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PointLogs'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
