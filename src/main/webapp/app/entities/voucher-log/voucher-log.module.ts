import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyProject7SharedModule } from 'app/shared';
import { MyProject7AdminModule } from 'app/admin/admin.module';
import {
  VoucherLogComponent,
  VoucherLogDetailComponent,
  VoucherLogUpdateComponent,
  VoucherLogDeletePopupComponent,
  VoucherLogDeleteDialogComponent,
  voucherLogRoute,
  voucherLogPopupRoute
} from './';

const ENTITY_STATES = [...voucherLogRoute, ...voucherLogPopupRoute];

@NgModule({
  imports: [MyProject7SharedModule, MyProject7AdminModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VoucherLogComponent,
    VoucherLogDetailComponent,
    VoucherLogUpdateComponent,
    VoucherLogDeleteDialogComponent,
    VoucherLogDeletePopupComponent
  ],
  entryComponents: [VoucherLogComponent, VoucherLogUpdateComponent, VoucherLogDeleteDialogComponent, VoucherLogDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyProject7VoucherLogModule {}
