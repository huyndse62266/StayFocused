/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { PointLogDetailComponent } from 'app/entities/point-log/point-log-detail.component';
import { PointLog } from 'app/shared/model/point-log.model';

describe('Component Tests', () => {
  describe('PointLog Management Detail Component', () => {
    let comp: PointLogDetailComponent;
    let fixture: ComponentFixture<PointLogDetailComponent>;
    const route = ({ data: of({ pointLog: new PointLog(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [PointLogDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PointLogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PointLogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pointLog).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
