/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { ListStoreUpdateComponent } from 'app/entities/list-store/list-store-update.component';
import { ListStoreService } from 'app/entities/list-store/list-store.service';
import { ListStore } from 'app/shared/model/list-store.model';

describe('Component Tests', () => {
  describe('ListStore Management Update Component', () => {
    let comp: ListStoreUpdateComponent;
    let fixture: ComponentFixture<ListStoreUpdateComponent>;
    let service: ListStoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [ListStoreUpdateComponent]
      })
        .overrideTemplate(ListStoreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ListStoreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ListStoreService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new ListStore(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.listStore = entity;
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
          const entity = new ListStore();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.listStore = entity;
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
