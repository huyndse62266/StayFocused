/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyProject7TestModule } from '../../../test.module';
import { ActivitiesLogUpdateComponent } from 'app/entities/activities-log/activities-log-update.component';
import { ActivitiesLogService } from 'app/entities/activities-log/activities-log.service';
import { ActivitiesLog } from 'app/shared/model/activities-log.model';

describe('Component Tests', () => {
  describe('ActivitiesLog Management Update Component', () => {
    let comp: ActivitiesLogUpdateComponent;
    let fixture: ComponentFixture<ActivitiesLogUpdateComponent>;
    let service: ActivitiesLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MyProject7TestModule],
        declarations: [ActivitiesLogUpdateComponent]
      })
        .overrideTemplate(ActivitiesLogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActivitiesLogUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActivitiesLogService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new ActivitiesLog(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.activitiesLog = entity;
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
          const entity = new ActivitiesLog();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.activitiesLog = entity;
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
