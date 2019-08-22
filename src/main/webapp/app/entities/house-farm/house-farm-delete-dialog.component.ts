import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHouseFarm } from 'app/shared/model/house-farm.model';
import { HouseFarmService } from './house-farm.service';

@Component({
  selector: 'jhi-house-farm-delete-dialog',
  templateUrl: './house-farm-delete-dialog.component.html'
})
export class HouseFarmDeleteDialogComponent {
  houseFarm: IHouseFarm;

  constructor(protected houseFarmService: HouseFarmService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.houseFarmService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'houseFarmListModification',
        content: 'Deleted an houseFarm'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-house-farm-delete-popup',
  template: ''
})
export class HouseFarmDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ houseFarm }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(HouseFarmDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.houseFarm = houseFarm;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/house-farm']);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/house-farm']);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
