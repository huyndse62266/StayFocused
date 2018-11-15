/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { StoreGroupUpdateComponent } from 'app/entities/store-group/store-group-update.component';
import { StoreGroupService } from 'app/entities/store-group/store-group.service';
import { StoreGroup } from 'app/shared/model/store-group.model';

describe('Component Tests', () => {
  describe('StoreGroup Management Update Component', () => {
    let comp: StoreGroupUpdateComponent;
    let fixture: ComponentFixture<StoreGroupUpdateComponent>;
    let service: StoreGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [StoreGroupUpdateComponent]
      })
        .overrideTemplate(StoreGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StoreGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StoreGroupService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new StoreGroup(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.storeGroup = entity;
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
          const entity = new StoreGroup();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.storeGroup = entity;
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
