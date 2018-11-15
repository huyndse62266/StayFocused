/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { VoucherLogDetailComponent } from 'app/entities/voucher-log/voucher-log-detail.component';
import { VoucherLog } from 'app/shared/model/voucher-log.model';

describe('Component Tests', () => {
  describe('VoucherLog Management Detail Component', () => {
    let comp: VoucherLogDetailComponent;
    let fixture: ComponentFixture<VoucherLogDetailComponent>;
    const route = ({ data: of({ voucherLog: new VoucherLog(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [VoucherLogDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VoucherLogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VoucherLogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.voucherLog).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
