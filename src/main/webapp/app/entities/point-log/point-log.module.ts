import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyProject7SharedModule } from 'app/shared';
import { MyProject7AdminModule } from 'app/admin/admin.module';
import {
  PointLogComponent,
  PointLogDetailComponent,
  PointLogUpdateComponent,
  PointLogDeletePopupComponent,
  PointLogDeleteDialogComponent,
  pointLogRoute,
  pointLogPopupRoute
} from './';

const ENTITY_STATES = [...pointLogRoute, ...pointLogPopupRoute];

@NgModule({
  imports: [MyProject7SharedModule, MyProject7AdminModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PointLogComponent,
    PointLogDetailComponent,
    PointLogUpdateComponent,
    PointLogDeleteDialogComponent,
    PointLogDeletePopupComponent
  ],
  entryComponents: [PointLogComponent, PointLogUpdateComponent, PointLogDeleteDialogComponent, PointLogDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyProject7PointLogModule {}
