/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { PointLogUpdateComponent } from 'app/entities/point-log/point-log-update.component';
import { PointLogService } from 'app/entities/point-log/point-log.service';
import { PointLog } from 'app/shared/model/point-log.model';

describe('Component Tests', () => {
  describe('PointLog Management Update Component', () => {
    let comp: PointLogUpdateComponent;
    let fixture: ComponentFixture<PointLogUpdateComponent>;
    let service: PointLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [PointLogUpdateComponent]
      })
        .overrideTemplate(PointLogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PointLogUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PointLogService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new PointLog(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.pointLog = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );

      it(
        'Should call create service on save for new entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new PointLog();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.pointLog = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );
    });
  });
});
