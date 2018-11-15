/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { StoreTypeDetailComponent } from 'app/entities/store-type/store-type-detail.component';
import { StoreType } from 'app/shared/model/store-type.model';

describe('Component Tests', () => {
  describe('StoreType Management Detail Component', () => {
    let comp: StoreTypeDetailComponent;
    let fixture: ComponentFixture<StoreTypeDetailComponent>;
    const route = ({ data: of({ storeType: new StoreType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [StoreTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StoreTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StoreTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.storeType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
