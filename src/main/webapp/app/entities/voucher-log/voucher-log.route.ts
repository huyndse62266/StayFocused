import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { VoucherLog } from 'app/shared/model/voucher-log.model';
import { VoucherLogService } from './voucher-log.service';
import { VoucherLogComponent } from './voucher-log.component';
import { VoucherLogDetailComponent } from './voucher-log-detail.component';
import { VoucherLogUpdateComponent } from './voucher-log-update.component';
import { VoucherLogDeletePopupComponent } from './voucher-log-delete-dialog.component';
import { IVoucherLog } from 'app/shared/model/voucher-log.model';

@Injectable({ providedIn: 'root' })
export class VoucherLogResolve implements Resolve<IVoucherLog> {
  constructor(private service: VoucherLogService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((voucherLog: HttpResponse<VoucherLog>) => voucherLog.body));
    }
    return of(new VoucherLog());
  }
}

export const voucherLogRoute: Routes = [
  {
    path: 'voucher-log',
    component: VoucherLogComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VoucherLogs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'voucher-log/:id/view',
    component: VoucherLogDetailComponent,
    resolve: {
      voucherLog: VoucherLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VoucherLogs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'voucher-log/new',
    component: VoucherLogUpdateComponent,
    resolve: {
      voucherLog: VoucherLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VoucherLogs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'voucher-log/:id/edit',
    component: VoucherLogUpdateComponent,
    resolve: {
      voucherLog: VoucherLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VoucherLogs'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const voucherLogPopupRoute: Routes = [
  {
    path: 'voucher-log/:id/delete',
    component: VoucherLogDeletePopupComponent,
    resolve: {
      voucherLog: VoucherLogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'VoucherLogs'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
