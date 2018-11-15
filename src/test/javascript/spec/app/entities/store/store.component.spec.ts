/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyProject7TestModule } from '../../../test.module';
import { StoreComponent } from 'app/entities/store/store.component';
import { StoreService } from 'app/entities/store/store.service';
import { Store } from 'app/shared/model/store.model';

describe('Component Tests', () => {
  describe('StoreGroup Management Component', () => {
    let comp: StoreComponent;
    let fixture: ComponentFixture<StoreComponent>;
    let service: StoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [StoreComponent],
        providers: []
      })
        .overrideTemplate(StoreComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StoreComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StoreService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Store(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.stores[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
