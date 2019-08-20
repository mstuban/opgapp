/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OpgappTestModule } from '../../../test.module';
import { HouseFarmDeleteDialogComponent } from 'app/entities/house-farm/house-farm-delete-dialog.component';
import { HouseFarmService } from 'app/entities/house-farm/house-farm.service';

describe('Component Tests', () => {
  describe('HouseFarm Management Delete Component', () => {
    let comp: HouseFarmDeleteDialogComponent;
    let fixture: ComponentFixture<HouseFarmDeleteDialogComponent>;
    let service: HouseFarmService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OpgappTestModule],
        declarations: [HouseFarmDeleteDialogComponent]
      })
        .overrideTemplate(HouseFarmDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HouseFarmDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HouseFarmService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
