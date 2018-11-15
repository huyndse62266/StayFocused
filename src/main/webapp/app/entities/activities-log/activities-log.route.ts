import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ActivitiesLog } from 'app/shared/model/activities-log.model';
import { ActivitiesLogService } from './activities-log.service';
import { ActivitiesLogComponent } from './activities-log.component';
import { ActivitiesLogDetailComponent } from './activities-log-detail.component';
import { ActivitiesLogUpdateComponent } from './activities-log-update.component';
import { ActivitiesLogDeletePopupComponent } from './activities-log-delete-dialog.component';
import { IActivitiesLog } from 'app/shared/model/activities-log.model';

@Injectable({ providedIn: 'root' })
export class ActivitiesLogResolve implements Resolve<IActivitiesLog> {
  constructor(private service: ActivitiesLogService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((activitiesLog: HttpResponse<ActivitiesLog>) => activitiesLog.body));
    }
    return of(new ActivitiesLog());
  }
}

export const activitiesLogRoute: Routes = [
  {
    path: 'activities-log',
    component: ActivitiesLogComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ActivitiesLogs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'activities-log/:id/view',
    component: ActivitiesLogDetailComponent,
    resolve: {
      activitiesLog: ActivitiesLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ActivitiesLogs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'activities-log/new',
    component: ActivitiesLogUpdateComponent,
    resolve: {
      activitiesLog: ActivitiesLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ActivitiesLogs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'activities-log/:id/edit',
    component: ActivitiesLogUpdateComponent,
    resolve: {
      activitiesLog: ActivitiesLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ActivitiesLogs'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const activitiesLogPopupRoute: Routes = [
  {
    path: 'activities-log/:id/delete',
    component: ActivitiesLogDeletePopupComponent,
    resolve: {
      activitiesLog: ActivitiesLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ActivitiesLogs'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
