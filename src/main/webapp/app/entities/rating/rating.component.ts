import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRating } from 'app/shared/model/rating.model';
import { AccountService } from 'app/core';
import { RatingService } from './rating.service';
import {IProduct} from 'app/shared/model/product.model';

@Component({
  selector: 'jhi-rating',
  templateUrl: './rating.component.html'
})
export class RatingComponent implements OnInit, OnDestroy {
  ratings: IRating[];
  currentAccount: any;
  eventSubscriber: Subscription;
  product: IProduct;

  constructor(
    protected ratingService: RatingService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.ratingService
      .query()
      .pipe(
        filter((res: HttpResponse<IRating[]>) => res.ok),
        map((res: HttpResponse<IRating[]>) => res.body)
      )
      .subscribe(
        (res: IRating[]) => {
          this.ratings = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRatings();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRating) {
    return item.id;
  }

  registerChangeInRatings() {
    this.eventSubscriber = this.eventManager.subscribe('ratingListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
