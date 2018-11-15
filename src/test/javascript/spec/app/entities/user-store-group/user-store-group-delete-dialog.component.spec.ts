/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MyProject7TestModule } from '../../../test.module';
import { UserStoreGroupDeleteDialogComponent } from 'app/entities/user-store-group/user-store-group-delete-dialog.component';
import { UserStoreGroupService } from 'app/entities/user-store-group/user-store-group.service';

describe('Component Tests', () => {
  describe('UserStoreGroup Management Delete Component', () => {
    let comp: UserStoreGroupDeleteDialogComponent;
    let fixture: ComponentFixture<UserStoreGroupDeleteDialogComponent>;
    let service: UserStoreGroupService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [UserStoreGroupDeleteDialogComponent]
      })
        .overrideTemplate(UserStoreGroupDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserStoreGroupDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserStoreGroupService);
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
