import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyProject7SharedModule } from 'app/shared';
import { MyProject7AdminModule } from 'app/admin/admin.module';
import {
  UserStoreGroupComponent,
  UserStoreGroupDetailComponent,
  UserStoreGroupUpdateComponent,
  UserStoreGroupDeletePopupComponent,
  UserStoreGroupDeleteDialogComponent,
  userStoreGroupRoute,
  userStoreGroupPopupRoute
} from './';

const ENTITY_STATES = [...userStoreGroupRoute, ...userStoreGroupPopupRoute];

@NgModule({
  imports: [MyProject7SharedModule, MyProject7AdminModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserStoreGroupComponent,
    UserStoreGroupDetailComponent,
    UserStoreGroupUpdateComponent,
    UserStoreGroupDeleteDialogComponent,
    UserStoreGroupDeletePopupComponent
  ],
  entryComponents: [
    UserStoreGroupComponent,
    UserStoreGroupUpdateComponent,
    UserStoreGroupDeleteDialogComponent,
    UserStoreGroupDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyProject7UserStoreGroupModule {}
