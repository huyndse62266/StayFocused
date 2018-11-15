/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyProject7TestModule } from '../../../test.module';
import { PointLogComponent } from 'app/entities/point-log/point-log.component';
import { PointLogService } from 'app/entities/point-log/point-log.service';
import { PointLog } from 'app/shared/model/point-log.model';

describe('Component Tests', () => {
  describe('PointLog Management Component', () => {
    let comp: PointLogComponent;
    let fixture: ComponentFixture<PointLogComponent>;
    let service: PointLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [PointLogComponent],
        providers: []
      })
        .overrideTemplate(PointLogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PointLogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PointLogService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PointLog(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pointLogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
