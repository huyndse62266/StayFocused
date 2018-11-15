/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { ListStoreDetailComponent } from 'app/entities/list-store/list-store-detail.component';
import { ListStore } from 'app/shared/model/list-store.model';

describe('Component Tests', () => {
  describe('ListStore Management Detail Component', () => {
    let comp: ListStoreDetailComponent;
    let fixture: ComponentFixture<ListStoreDetailComponent>;
    const route = ({ data: of({ listStore: new ListStore(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [ListStoreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ListStoreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ListStoreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.listStore).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
