/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyProject7TestModule } from '../../../test.module';
import { StoreGroupDeleteDialogComponent } from 'app/entities/store-group/store-group-delete-dialog.component';
import { StoreGroupService } from 'app/entities/store-group/store-group.service';

describe('Component Tests', () => {
  describe('StoreGroup Management Delete Component', () => {
    let comp: StoreGroupDeleteDialogComponent;
    let fixture: ComponentFixture<StoreGroupDeleteDialogComponent>;
    let service: StoreGroupService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [StoreGroupDeleteDialogComponent]
      })
        .overrideTemplate(StoreGroupDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StoreGroupDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StoreGroupService);
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
