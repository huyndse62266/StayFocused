import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyProject7SharedModule } from 'app/shared';
import { MyProject7AdminModule } from 'app/admin/admin.module';
import {
  ActivitiesLogComponent,
  ActivitiesLogDetailComponent,
  ActivitiesLogUpdateComponent,
  ActivitiesLogDeletePopupComponent,
  ActivitiesLogDeleteDialogComponent,
  activitiesLogRoute,
  activitiesLogPopupRoute
} from './';

const ENTITY_STATES = [...activitiesLogRoute, ...activitiesLogPopupRoute];

@NgModule({
  imports: [MyProject7SharedModule, MyProject7AdminModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ActivitiesLogComponent,
    ActivitiesLogDetailComponent,
    ActivitiesLogUpdateComponent,
    ActivitiesLogDeleteDialogComponent,
    ActivitiesLogDeletePopupComponent
  ],
  entryComponents: [
    ActivitiesLogComponent,
    ActivitiesLogUpdateComponent,
    ActivitiesLogDeleteDialogComponent,
    ActivitiesLogDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyProject7ActivitiesLogModule {}
