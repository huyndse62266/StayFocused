/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { StoreTypeUpdateComponent } from 'app/entities/store-type/store-type-update.component';
import { StoreTypeService } from 'app/entities/store-type/store-type.service';
import { StoreType } from 'app/shared/model/store-type.model';

describe('Component Tests', () => {
  describe('StoreType Management Update Component', () => {
    let comp: StoreTypeUpdateComponent;
    let fixture: ComponentFixture<StoreTypeUpdateComponent>;
    let service: StoreTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [StoreTypeUpdateComponent]
      })
        .overrideTemplate(StoreTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StoreTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StoreTypeService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new StoreType(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.storeType = entity;
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
          const entity = new StoreType();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.storeType = entity;
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
