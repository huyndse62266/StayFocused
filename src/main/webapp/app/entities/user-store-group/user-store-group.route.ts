import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserStoreGroup } from 'app/shared/model/user-store-group.model';
import { UserStoreGroupService } from './user-store-group.service';
import { UserStoreGroupComponent } from './user-store-group.component';
import { UserStoreGroupDetailComponent } from './user-store-group-detail.component';
import { UserStoreGroupUpdateComponent } from './user-store-group-update.component';
import { UserStoreGroupDeletePopupComponent } from './user-store-group-delete-dialog.component';
import { IUserStoreGroup } from 'app/shared/model/user-store-group.model';

@Injectable({ providedIn: 'root' })
export class UserStoreGroupResolve implements Resolve<IUserStoreGroup> {
  constructor(private service: UserStoreGroupService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((userStoreGroup: HttpResponse<UserStoreGroup>) => userStoreGroup.body));
    }
    return of(new UserStoreGroup());
  }
}

export const userStoreGroupRoute: Routes = [
  {
    path: 'user-store-group',
    component: UserStoreGroupComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserStoreGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'user-store-group/:id/view',
    component: UserStoreGroupDetailComponent,
    resolve: {
      userStoreGroup: UserStoreGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserStoreGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'user-store-group/new',
    component: UserStoreGroupUpdateComponent,
    resolve: {
      userStoreGroup: UserStoreGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserStoreGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'user-store-group/:id/edit',
    component: UserStoreGroupUpdateComponent,
    resolve: {
      userStoreGroup: UserStoreGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserStoreGroups'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userStoreGroupPopupRoute: Routes = [
  {
    path: 'user-store-group/:id/delete',
    component: UserStoreGroupDeletePopupComponent,
    resolve: {
      userStoreGroup: UserStoreGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UserStoreGroups'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
