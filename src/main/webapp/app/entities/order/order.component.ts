import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import {IOrder, Order} from 'app/shared/model/order.model';
import { AccountService } from 'app/core';
import { OrderService } from './order.service';
import {IProduct} from "app/shared/model/product.model";
import {SessionStorageService} from "ngx-webstorage";
import {Router} from "@angular/router";
import {EventService} from "app/core/event/event.service";

@Component({
  selector: 'jhi-order',
  templateUrl: './order.component.html'
})
export class OrderComponent implements OnInit, OnDestroy {
  orders: IOrder[];
  currentAccount: any;
  eventSubscriber: Subscription;
  cartProducts: IProduct[];
  totalPrice: number = 0;

  constructor(
    protected orderService: OrderService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
    protected sessionStorage: SessionStorageService,
    protected eventService: EventService,
    protected router: Router
  ) {}

  loadAll() {
    this.totalPrice = 0;
    this.orderService
      .query()
      .pipe(
        filter((res: HttpResponse<IOrder[]>) => res.ok),
        map((res: HttpResponse<IOrder[]>) => res.body)
      )
      .subscribe(
        (res: IOrder[]) => {
          this.orders = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.cartProducts = this.sessionStorage.retrieve("cartProducts");

    for (let i = 0;i < this.cartProducts.length; i++) {
      this.totalPrice = this.totalPrice + this.cartProducts[i].price;
    }
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInOrders();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOrder) {
    return item.id;
  }

  registerChangeInOrders() {
    this.eventSubscriber = this.eventManager.subscribe('orderListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  finishOrder() {
    let order = new Order();
    order.products = this.cartProducts;
    order.user = this.currentAccount;
    order.totalPrice = this.totalPrice;
    this.orderService
      .sendOrderMail(order)
      .pipe(
        filter((res: HttpResponse<IOrder>) => res.ok),
        map((res: HttpResponse<IOrder>) => res.body))
      .subscribe(
            (res: IOrder) => {
              this.eventService.cartEmptied.emit();
              setTimeout( () =>
              {this.jhiAlertService.success('orderSent')},
              0);
              setTimeout( () =>
              {this.router.navigate([''])},
                2000);

            },
            (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  removeFromCart(productId: number) {
    let index;
    for (let i = 0;i < this.cartProducts.length; i++) {
      if (this.cartProducts[i].id === productId) {
        index = i;
      }
    }

    this.cartProducts.splice(index, 1);
    this.sessionStorage.store("cartProducts", this.cartProducts);
    for (let i = 0;i < this.cartProducts.length; i++) {
      this.totalPrice = this.totalPrice + this.cartProducts[i].price;
    }
  }
}
