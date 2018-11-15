/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { StoreDetailComponent } from 'app/entities/store/store-detail.component';
import { Store } from 'app/shared/model/store.model';

describe('Component Tests', () => {
  describe('StoreGroup Management Detail Component', () => {
    let comp: StoreDetailComponent;
    let fixture: ComponentFixture<StoreDetailComponent>;
    const route = ({ data: of({ store: new Store(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [StoreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StoreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StoreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.store).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
