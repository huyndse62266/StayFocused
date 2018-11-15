/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { UserStoreGroupUpdateComponent } from 'app/entities/user-store-group/user-store-group-update.component';
import { UserStoreGroupService } from 'app/entities/user-store-group/user-store-group.service';
import { UserStoreGroup } from 'app/shared/model/user-store-group.model';

describe('Component Tests', () => {
  describe('UserStoreGroup Management Update Component', () => {
    let comp: UserStoreGroupUpdateComponent;
    let fixture: ComponentFixture<UserStoreGroupUpdateComponent>;
    let service: UserStoreGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [UserStoreGroupUpdateComponent]
      })
        .overrideTemplate(UserStoreGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserStoreGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserStoreGroupService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new UserStoreGroup(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.userStoreGroup = entity;
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
          const entity = new UserStoreGroup();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.userStoreGroup = entity;
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
