/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyProject7TestModule } from '../../../test.module';
import { VoucherLogComponent } from 'app/entities/voucher-log/voucher-log.component';
import { VoucherLogService } from 'app/entities/voucher-log/voucher-log.service';
import { VoucherLog } from 'app/shared/model/voucher-log.model';

describe('Component Tests', () => {
  describe('VoucherLog Management Component', () => {
    let comp: VoucherLogComponent;
    let fixture: ComponentFixture<VoucherLogComponent>;
    let service: VoucherLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [VoucherLogComponent],
        providers: []
      })
        .overrideTemplate(VoucherLogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoucherLogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VoucherLogService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VoucherLog(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.voucherLogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
