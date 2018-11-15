import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ListStore } from 'app/shared/model/list-store.model';
import { ListStoreService } from './list-store.service';
import { ListStoreComponent } from './list-store.component';
import { ListStoreDetailComponent } from './list-store-detail.component';
import { ListStoreUpdateComponent } from './list-store-update.component';
import { ListStoreDeletePopupComponent } from './list-store-delete-dialog.component';
import { IListStore } from 'app/shared/model/list-store.model';

@Injectable({ providedIn: 'root' })
export class ListStoreResolve implements Resolve<IListStore> {
  constructor(private service: ListStoreService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((listStore: HttpResponse<ListStore>) => listStore.body));
    }
    return of(new ListStore());
  }
}

export const listStoreRoute: Routes = [
  {
    path: 'list-store',
    component: ListStoreComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ListStores'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'list-store/:id/view',
    component: ListStoreDetailComponent,
    resolve: {
      listStore: ListStoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ListStores'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'list-store/new',
    component: ListStoreUpdateComponent,
    resolve: {
      listStore: ListStoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ListStores'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'list-store/:id/edit',
    component: ListStoreUpdateComponent,
    resolve: {
      listStore: ListStoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ListStores'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const listStorePopupRoute: Routes = [
  {
    path: 'list-store/:id/delete',
    component: ListStoreDeletePopupComponent,
    resolve: {
      listStore: ListStoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ListStores'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
