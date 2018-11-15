import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyProject7SharedModule } from 'app/shared';
import {
  StoreTypeComponent,
  StoreTypeDetailComponent,
  StoreTypeUpdateComponent,
  StoreTypeDeletePopupComponent,
  StoreTypeDeleteDialogComponent,
  storeTypeRoute,
  storeTypePopupRoute
} from './';

const ENTITY_STATES = [...storeTypeRoute, ...storeTypePopupRoute];

@NgModule({
  imports: [MyProject7SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StoreTypeComponent,
    StoreTypeDetailComponent,
    StoreTypeUpdateComponent,
    StoreTypeDeleteDialogComponent,
    StoreTypeDeletePopupComponent
  ],
  entryComponents: [StoreTypeComponent, StoreTypeUpdateComponent, StoreTypeDeleteDialogComponent, StoreTypeDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyProject7StoreTypeModule {}
