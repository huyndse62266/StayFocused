/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyProject7TestModule } from '../../../test.module';
import { UserStoreGroupComponent } from 'app/entities/user-store-group/user-store-group.component';
import { UserStoreGroupService } from 'app/entities/user-store-group/user-store-group.service';
import { UserStoreGroup } from 'app/shared/model/user-store-group.model';

describe('Component Tests', () => {
  describe('UserStoreGroup Management Component', () => {
    let comp: UserStoreGroupComponent;
    let fixture: ComponentFixture<UserStoreGroupComponent>;
    let service: UserStoreGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [UserStoreGroupComponent],
        providers: []
      })
        .overrideTemplate(UserStoreGroupComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserStoreGroupComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserStoreGroupService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserStoreGroup(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userStoreGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
