/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { ActivitiesLogDetailComponent } from 'app/entities/activities-log/activities-log-detail.component';
import { ActivitiesLog } from 'app/shared/model/activities-log.model';

describe('Component Tests', () => {
  describe('ActivitiesLog Management Detail Component', () => {
    let comp: ActivitiesLogDetailComponent;
    let fixture: ComponentFixture<ActivitiesLogDetailComponent>;
    const route = ({ data: of({ activitiesLog: new ActivitiesLog(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [ActivitiesLogDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ActivitiesLogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActivitiesLogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.activitiesLog).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
