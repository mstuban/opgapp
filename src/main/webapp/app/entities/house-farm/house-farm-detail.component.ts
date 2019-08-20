import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHouseFarm } from 'app/shared/model/house-farm.model';

@Component({
  selector: 'jhi-house-farm-detail',
  templateUrl: './house-farm-detail.component.html'
})
export class HouseFarmDetailComponent implements OnInit {
  houseFarm: IHouseFarm;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ houseFarm }) => {
      this.houseFarm = houseFarm;
    });
  }

  previousState() {
    window.history.back();
  }
}
