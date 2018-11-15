/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyProject7TestModule } from '../../../test.module';
import { ListStoreDeleteDialogComponent } from 'app/entities/list-store/list-store-delete-dialog.component';
import { ListStoreService } from 'app/entities/list-store/list-store.service';

describe('Component Tests', () => {
  describe('ListStore Management Delete Component', () => {
    let comp: ListStoreDeleteDialogComponent;
    let fixture: ComponentFixture<ListStoreDeleteDialogComponent>;
    let service: ListStoreService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [ListStoreDeleteDialogComponent]
      })
        .overrideTemplate(ListStoreDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ListStoreDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ListStoreService);
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
