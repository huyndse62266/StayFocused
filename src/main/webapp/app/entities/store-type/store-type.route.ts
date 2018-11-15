import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { StoreType } from 'app/shared/model/store-type.model';
import { StoreTypeService } from './store-type.service';
import { StoreTypeComponent } from './store-type.component';
import { StoreTypeDetailComponent } from './store-type-detail.component';
import { StoreTypeUpdateComponent } from './store-type-update.component';
import { StoreTypeDeletePopupComponent } from './store-type-delete-dialog.component';
import { IStoreType } from 'app/shared/model/store-type.model';

@Injectable({ providedIn: 'root' })
export class StoreTypeResolve implements Resolve<IStoreType> {
  constructor(private service: StoreTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((storeType: HttpResponse<StoreType>) => storeType.body));
    }
    return of(new StoreType());
  }
}

export const storeTypeRoute: Routes = [
  {
    path: 'store-type',
    component: StoreTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StoreTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'store-type/:id/view',
    component: StoreTypeDetailComponent,
    resolve: {
      storeType: StoreTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StoreTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'store-type/new',
    component: StoreTypeUpdateComponent,
    resolve: {
      storeType: StoreTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StoreTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'store-type/:id/edit',
    component: StoreTypeUpdateComponent,
    resolve: {
      storeType: StoreTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StoreTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const storeTypePopupRoute: Routes = [
  {
    path: 'store-type/:id/delete',
    component: StoreTypeDeletePopupComponent,
    resolve: {
      storeType: StoreTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StoreTypes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
