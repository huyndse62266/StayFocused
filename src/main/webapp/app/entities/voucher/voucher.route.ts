import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Voucher } from 'app/shared/model/voucher.model';
import { VoucherService } from './voucher.service';
import { VoucherComponent } from './voucher.component';
import { VoucherDetailComponent } from './voucher-detail.component';
import { VoucherUpdateComponent } from './voucher-update.component';
import { VoucherDeletePopupComponent } from './voucher-delete-dialog.component';
import { IVoucher } from 'app/shared/model/voucher.model';

@Injectable({ providedIn: 'root' })
export class VoucherResolve implements Resolve<IVoucher> {
  constructor(private service: VoucherService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((voucher: HttpResponse<Voucher>) => voucher.body));
    }
    return of(new Voucher());
  }
}

export const voucherRoute: Routes = [
  {
    path: 'voucher',
    component: VoucherComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vouchers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'voucher/:id/view',
    component: VoucherDetailComponent,
    resolve: {
      voucher: VoucherResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vouchers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'voucher/new',
    component: VoucherUpdateComponent,
    resolve: {
      voucher: VoucherResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vouchers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'voucher/:id/edit',
    component: VoucherUpdateComponent,
    resolve: {
      voucher: VoucherResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vouchers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const voucherPopupRoute: Routes = [
  {
    path: 'voucher/:id/delete',
    component: VoucherDeletePopupComponent,
    resolve: {
      voucher: VoucherResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Vouchers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
