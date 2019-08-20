/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { OpgappTestModule } from '../../../test.module';
import { HouseFarmUpdateComponent } from 'app/entities/house-farm/house-farm-update.component';
import { HouseFarmService } from 'app/entities/house-farm/house-farm.service';
import { HouseFarm } from 'app/shared/model/house-farm.model';

describe('Component Tests', () => {
  describe('HouseFarm Management Update Component', () => {
    let comp: HouseFarmUpdateComponent;
    let fixture: ComponentFixture<HouseFarmUpdateComponent>;
    let service: HouseFarmService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OpgappTestModule],
        declarations: [HouseFarmUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HouseFarmUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HouseFarmUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HouseFarmService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HouseFarm(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new HouseFarm();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
