import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Store } from 'app/shared/model/store.model';
import { StoreService } from './store.service';
import { StoreComponent } from './store.component';
import { StoreDetailComponent } from './store-detail.component';
import { StoreUpdateComponent } from './store-update.component';
import { StoreDeletePopupComponent } from './store-delete-dialog.component';
import { IStore } from 'app/shared/model/store.model';

@Injectable({ providedIn: 'root' })
export class StoreResolve implements Resolve<IStore> {
  constructor(private service: StoreService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((store: HttpResponse<Store>) => store.body));
    }
    return of(new Store());
  }
}

export const storeRoute: Routes = [
  {
    path: 'store',
    component: StoreComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Stores'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'store/:id/view',
    component: StoreDetailComponent,
    resolve: {
      store: StoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Stores'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'store/new',
    component: StoreUpdateComponent,
    resolve: {
      store: StoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Stores'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'store/:id/edit',
    component: StoreUpdateComponent,
    resolve: {
      store: StoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Stores'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const storePopupRoute: Routes = [
  {
    path: 'store/:id/delete',
    component: StoreDeletePopupComponent,
    resolve: {
      store: StoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Stores'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
