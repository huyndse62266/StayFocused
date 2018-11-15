/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyProject7TestModule } from '../../../test.module';
import { VoucherDeleteDialogComponent } from 'app/entities/voucher/voucher-delete-dialog.component';
import { VoucherService } from 'app/entities/voucher/voucher.service';

describe('Component Tests', () => {
  describe('Voucher Management Delete Component', () => {
    let comp: VoucherDeleteDialogComponent;
    let fixture: ComponentFixture<VoucherDeleteDialogComponent>;
    let service: VoucherService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [VoucherDeleteDialogComponent]
      })
        .overrideTemplate(VoucherDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VoucherDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VoucherService);
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
