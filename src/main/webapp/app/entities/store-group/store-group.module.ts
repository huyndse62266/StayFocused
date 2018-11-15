import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyProject7SharedModule } from 'app/shared';
import {
  StoreGroupComponent,
  StoreGroupDetailComponent,
  StoreGroupUpdateComponent,
  StoreGroupDeletePopupComponent,
  StoreGroupDeleteDialogComponent,
  storeGroupRoute,
  storeGroupPopupRoute
} from './';

const ENTITY_STATES = [...storeGroupRoute, ...storeGroupPopupRoute];

@NgModule({
  imports: [MyProject7SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StoreGroupComponent,
    StoreGroupDetailComponent,
    StoreGroupUpdateComponent,
    StoreGroupDeleteDialogComponent,
    StoreGroupDeletePopupComponent
  ],
  entryComponents: [StoreGroupComponent, StoreGroupUpdateComponent, StoreGroupDeleteDialogComponent, StoreGroupDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyProject7StoreGroupModule {}
