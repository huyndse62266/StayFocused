/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyProject7TestModule } from '../../../test.module';
import { StoreTypeComponent } from 'app/entities/store-type/store-type.component';
import { StoreTypeService } from 'app/entities/store-type/store-type.service';
import { StoreType } from 'app/shared/model/store-type.model';

describe('Component Tests', () => {
  describe('StoreType Management Component', () => {
    let comp: StoreTypeComponent;
    let fixture: ComponentFixture<StoreTypeComponent>;
    let service: StoreTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [StoreTypeComponent],
        providers: []
      })
        .overrideTemplate(StoreTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StoreTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StoreTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new StoreType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.storeTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
