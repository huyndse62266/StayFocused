/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { StoreGroupDetailComponent } from 'app/entities/store-group/store-group-detail.component';
import { StoreGroup } from 'app/shared/model/store-group.model';

describe('Component Tests', () => {
  describe('StoreGroup Management Detail Component', () => {
    let comp: StoreGroupDetailComponent;
    let fixture: ComponentFixture<StoreGroupDetailComponent>;
    const route = ({ data: of({ storeGroup: new StoreGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [StoreGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StoreGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StoreGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.storeGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
