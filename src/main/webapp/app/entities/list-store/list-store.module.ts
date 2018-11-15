import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyProject7SharedModule } from 'app/shared';
import {
  ListStoreComponent,
  ListStoreDetailComponent,
  ListStoreUpdateComponent,
  ListStoreDeletePopupComponent,
  ListStoreDeleteDialogComponent,
  listStoreRoute,
  listStorePopupRoute
} from './';

const ENTITY_STATES = [...listStoreRoute, ...listStorePopupRoute];

@NgModule({
  imports: [MyProject7SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ListStoreComponent,
    ListStoreDetailComponent,
    ListStoreUpdateComponent,
    ListStoreDeleteDialogComponent,
    ListStoreDeletePopupComponent
  ],
  entryComponents: [ListStoreComponent, ListStoreUpdateComponent, ListStoreDeleteDialogComponent, ListStoreDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyProject7ListStoreModule {}
