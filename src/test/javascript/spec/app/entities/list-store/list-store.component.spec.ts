/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyProject7TestModule } from '../../../test.module';
import { ListStoreComponent } from 'app/entities/list-store/list-store.component';
import { ListStoreService } from 'app/entities/list-store/list-store.service';
import { ListStore } from 'app/shared/model/list-store.model';

describe('Component Tests', () => {
  describe('ListStore Management Component', () => {
    let comp: ListStoreComponent;
    let fixture: ComponentFixture<ListStoreComponent>;
    let service: ListStoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [ListStoreComponent],
        providers: []
      })
        .overrideTemplate(ListStoreComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ListStoreComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ListStoreService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ListStore(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.listStores[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
