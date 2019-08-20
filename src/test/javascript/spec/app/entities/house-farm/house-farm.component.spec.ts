/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OpgappTestModule } from '../../../test.module';
import { HouseFarmComponent } from 'app/entities/house-farm/house-farm.component';
import { HouseFarmService } from 'app/entities/house-farm/house-farm.service';
import { HouseFarm } from 'app/shared/model/house-farm.model';

describe('Component Tests', () => {
  describe('HouseFarm Management Component', () => {
    let comp: HouseFarmComponent;
    let fixture: ComponentFixture<HouseFarmComponent>;
    let service: HouseFarmService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OpgappTestModule],
        declarations: [HouseFarmComponent],
        providers: []
      })
        .overrideTemplate(HouseFarmComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HouseFarmComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HouseFarmService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HouseFarm(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.houseFarms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
