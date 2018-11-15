/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyProject7TestModule } from '../../../test.module';
import { ActivitiesLogDeleteDialogComponent } from 'app/entities/activities-log/activities-log-delete-dialog.component';
import { ActivitiesLogService } from 'app/entities/activities-log/activities-log.service';

describe('Component Tests', () => {
  describe('ActivitiesLog Management Delete Component', () => {
    let comp: ActivitiesLogDeleteDialogComponent;
    let fixture: ComponentFixture<ActivitiesLogDeleteDialogComponent>;
    let service: ActivitiesLogService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [ActivitiesLogDeleteDialogComponent]
      })
        .overrideTemplate(ActivitiesLogDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActivitiesLogDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActivitiesLogService);
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
