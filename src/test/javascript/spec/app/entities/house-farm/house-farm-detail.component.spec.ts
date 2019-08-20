/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OpgappTestModule } from '../../../test.module';
import { HouseFarmDetailComponent } from 'app/entities/house-farm/house-farm-detail.component';
import { HouseFarm } from 'app/shared/model/house-farm.model';

describe('Component Tests', () => {
  describe('HouseFarm Management Detail Component', () => {
    let comp: HouseFarmDetailComponent;
    let fixture: ComponentFixture<HouseFarmDetailComponent>;
    const route = ({ data: of({ houseFarm: new HouseFarm(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OpgappTestModule],
        declarations: [HouseFarmDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HouseFarmDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HouseFarmDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.houseFarm).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
