/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyProject7TestModule } from '../../../test.module';
import { StoreGroupComponent } from 'app/entities/store-group/store-group.component';
import { StoreGroupService } from 'app/entities/store-group/store-group.service';
import { StoreGroup } from 'app/shared/model/store-group.model';

describe('Component Tests', () => {
  describe('StoreGroup Management Component', () => {
    let comp: StoreGroupComponent;
    let fixture: ComponentFixture<StoreGroupComponent>;
    let service: StoreGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [StoreGroupComponent],
        providers: []
      })
        .overrideTemplate(StoreGroupComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StoreGroupComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StoreGroupService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new StoreGroup(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.storeGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
