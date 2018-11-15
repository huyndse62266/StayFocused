import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { StoreGroup } from 'app/shared/model/store-group.model';
import { StoreGroupService } from './store-group.service';
import { StoreGroupComponent } from './store-group.component';
import { StoreGroupDetailComponent } from './store-group-detail.component';
import { StoreGroupUpdateComponent } from './store-group-update.component';
import { StoreGroupDeletePopupComponent } from './store-group-delete-dialog.component';
import { IStoreGroup } from 'app/shared/model/store-group.model';

@Injectable({ providedIn: 'root' })
export class StoreGroupResolve implements Resolve<IStoreGroup> {
  constructor(private service: StoreGroupService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((storeGroup: HttpResponse<StoreGroup>) => storeGroup.body));
    }
    return of(new StoreGroup());
  }
}

export const storeGroupRoute: Routes = [
  {
    path: 'store-group',
    component: StoreGroupComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StoreGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'store-group/:id/view',
    component: StoreGroupDetailComponent,
    resolve: {
      storeGroup: StoreGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StoreGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'store-group/new',
    component: StoreGroupUpdateComponent,
    resolve: {
      storeGroup: StoreGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StoreGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'store-group/:id/edit',
    component: StoreGroupUpdateComponent,
    resolve: {
      storeGroup: StoreGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StoreGroups'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const storeGroupPopupRoute: Routes = [
  {
    path: 'store-group/:id/delete',
    component: StoreGroupDeletePopupComponent,
    resolve: {
      storeGroup: StoreGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'StoreGroups'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
