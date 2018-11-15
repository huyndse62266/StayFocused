/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyProject7TestModule } from '../../../test.module';
import { VoucherLogDeleteDialogComponent } from 'app/entities/voucher-log/voucher-log-delete-dialog.component';
import { VoucherLogService } from 'app/entities/voucher-log/voucher-log.service';

describe('Component Tests', () => {
  describe('VoucherLog Management Delete Component', () => {
    let comp: VoucherLogDeleteDialogComponent;
    let fixture: ComponentFixture<VoucherLogDeleteDialogComponent>;
    let service: VoucherLogService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [VoucherLogDeleteDialogComponent]
      })
        .overrideTemplate(VoucherLogDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VoucherLogDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VoucherLogService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
