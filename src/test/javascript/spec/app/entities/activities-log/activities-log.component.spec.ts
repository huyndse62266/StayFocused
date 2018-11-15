/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyProject7TestModule } from '../../../test.module';
import { ActivitiesLogComponent } from 'app/entities/activities-log/activities-log.component';
import { ActivitiesLogService } from 'app/entities/activities-log/activities-log.service';
import { ActivitiesLog } from 'app/shared/model/activities-log.model';

describe('Component Tests', () => {
  describe('ActivitiesLog Management Component', () => {
    let comp: ActivitiesLogComponent;
    let fixture: ComponentFixture<ActivitiesLogComponent>;
    let service: ActivitiesLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [ActivitiesLogComponent],
        providers: []
      })
        .overrideTemplate(ActivitiesLogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActivitiesLogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActivitiesLogService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ActivitiesLog(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.activitiesLogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
