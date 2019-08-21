import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHouseFarm } from 'app/shared/model/house-farm.model';
import {AccountService} from "app/core";

@Component({
  selector: 'jhi-house-farm-detail',
  templateUrl: './house-farm-detail.component.html'
})
export class HouseFarmDetailComponent implements OnInit {
  houseFarm: IHouseFarm;
  private currentAccount: any;

  constructor(protected activatedRoute: ActivatedRoute, private accountService: AccountService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ houseFarm }) => {
      this.houseFarm = houseFarm;
    });

    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
  }

  previousState() {
    window.history.back();
  }
}
