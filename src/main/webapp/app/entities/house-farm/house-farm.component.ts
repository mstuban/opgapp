import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHouseFarm } from 'app/shared/model/house-farm.model';
import { AccountService } from 'app/core';
import { HouseFarmService } from './house-farm.service';

@Component({
  selector: 'jhi-house-farm',
  templateUrl: './house-farm.component.html'
})
export class HouseFarmComponent implements OnInit, OnDestroy {
  houseFarms: IHouseFarm[];
  currentAccount: any;
  eventSubscriber: Subscription;
  userAlreadyHasFarm: boolean;

  constructor(
    protected houseFarmService: HouseFarmService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.userAlreadyHasFarm = false;
    this.houseFarmService
      .query()
      .pipe(
        filter((res: HttpResponse<IHouseFarm[]>) => res.ok),
        map((res: HttpResponse<IHouseFarm[]>) => res.body)
      )
      .subscribe(
        (res: IHouseFarm[]) => {
          this.houseFarms = res;

          this.houseFarms.forEach(houseFarm =>
            {
              if (houseFarm.user.id === this.currentAccount.id) {
                this.userAlreadyHasFarm = true;
              }
            }
          );
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInHouseFarms();
    this.registerChangeInProducts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IHouseFarm) {
    return item.id;
  }

  registerChangeInHouseFarms() {
    this.eventSubscriber = this.eventManager.subscribe('houseFarmListModification', response => this.loadAll());
  }

  registerChangeInProducts() {
    this.eventSubscriber = this.eventManager.subscribe('productListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
