/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { StoreUpdateComponent } from 'app/entities/store/store-update.component';
import { StoreService } from 'app/entities/store/store.service';
import { Store } from 'app/shared/model/store.model';

describe('Component Tests', () => {
  describe('StoreGroup Management Update Component', () => {
    let comp: StoreUpdateComponent;
    let fixture: ComponentFixture<StoreUpdateComponent>;
    let service: StoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [StoreUpdateComponent]
      })
        .overrideTemplate(StoreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StoreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StoreService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new Store(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.store = entity;
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
          const entity = new Store();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.store = entity;
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
