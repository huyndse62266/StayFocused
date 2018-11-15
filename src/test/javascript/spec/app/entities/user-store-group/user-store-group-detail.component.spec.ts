/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { UserStoreGroupDetailComponent } from 'app/entities/user-store-group/user-store-group-detail.component';
import { UserStoreGroup } from 'app/shared/model/user-store-group.model';

describe('Component Tests', () => {
  describe('UserStoreGroup Management Detail Component', () => {
    let comp: UserStoreGroupDetailComponent;
    let fixture: ComponentFixture<UserStoreGroupDetailComponent>;
    const route = ({ data: of({ userStoreGroup: new UserStoreGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [UserStoreGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserStoreGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserStoreGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userStoreGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
