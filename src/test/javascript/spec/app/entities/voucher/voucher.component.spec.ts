/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyProject7TestModule } from '../../../test.module';
import { VoucherComponent } from 'app/entities/voucher/voucher.component';
import { VoucherService } from 'app/entities/voucher/voucher.service';
import { Voucher } from 'app/shared/model/voucher.model';

describe('Component Tests', () => {
  describe('Voucher Management Component', () => {
    let comp: VoucherComponent;
    let fixture: ComponentFixture<VoucherComponent>;
    let service: VoucherService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [VoucherComponent],
        providers: []
      })
        .overrideTemplate(VoucherComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoucherComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VoucherService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Voucher(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vouchers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
