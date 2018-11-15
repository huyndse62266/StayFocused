import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyProject7SharedModule } from 'app/shared';
import {
  RoleComponent,
  RoleDetailComponent,
  RoleUpdateComponent,
  RoleDeletePopupComponent,
  RoleDeleteDialogComponent,
  roleRoute,
  rolePopupRoute
} from './';

const ENTITY_STATES = [...roleRoute, ...rolePopupRoute];

@NgModule({
  imports: [MyProject7SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [RoleComponent, RoleDetailComponent, RoleUpdateComponent, RoleDeleteDialogComponent, RoleDeletePopupComponent],
  entryComponents: [RoleComponent, RoleUpdateComponent, RoleDeleteDialogComponent, RoleDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyProject7RoleModule {}
