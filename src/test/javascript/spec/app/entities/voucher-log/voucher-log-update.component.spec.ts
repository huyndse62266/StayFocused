/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { VoucherLogUpdateComponent } from 'app/entities/voucher-log/voucher-log-update.component';
import { VoucherLogService } from 'app/entities/voucher-log/voucher-log.service';
import { VoucherLog } from 'app/shared/model/voucher-log.model';

describe('Component Tests', () => {
  describe('VoucherLog Management Update Component', () => {
    let comp: VoucherLogUpdateComponent;
    let fixture: ComponentFixture<VoucherLogUpdateComponent>;
    let service: VoucherLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [VoucherLogUpdateComponent]
      })
        .overrideTemplate(VoucherLogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoucherLogUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VoucherLogService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new VoucherLog(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.voucherLog = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );

      it(
        'Should call create service on save for new entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new VoucherLog();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.voucherLog = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );
    });
  });
});
