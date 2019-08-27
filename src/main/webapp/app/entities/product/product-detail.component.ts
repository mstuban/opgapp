import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {IProduct} from 'app/shared/model/product.model';
import {AccountService} from "app/core";
import {IRating} from "app/shared/model/rating.model";
import {SessionStorageService} from "ngx-webstorage";
import {EventService} from "app/core/event/event.service";
import {JhiAlertService} from "ng-jhipster";

@Component({
  selector: 'jhi-product-detail',
  templateUrl: './product-detail.component.html'
})
export class ProductDetailComponent implements OnInit {
  product: IProduct;
  currentAccount: any;
  averageRating: number;
  cartProducts: IProduct[];

  constructor(protected activatedRoute: ActivatedRoute,
              protected accountService: AccountService,
              protected sessionStorage: SessionStorageService,
              protected eventService: EventService,
              protected jhiAlertService: JhiAlertService,
              ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.product = product;

      if (this.product.ratings.length > 0) {
        let totalSum = 0;
        this.product.ratings.forEach((rating: IRating) => {
          totalSum = totalSum + rating.stars;
        });
        this.averageRating = totalSum / this.product.ratings.length;
      }
    });

    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });

    this.cartProducts = this.sessionStorage.retrieve("cartProducts");

    if (this.cartProducts == null) {
      this.cartProducts = [];
    }

    this.eventService.loginCompleted.subscribe(
      () => {
        this.sessionStorage.clear("cartProducts");
        this.sessionStorage.store("cartProducts", []);
        this.cartProducts = [];
      }
    );

    this.eventService.cartEmptied.subscribe(
      () => {
        this.sessionStorage.clear("cartProducts");
        this.sessionStorage.store("cartProducts", []);
        this.cartProducts = [];
      }
    );
  }

  previousState() {
    window.history.back();
  }

  addProductToCart() {
    let cartProducts: any[];
    cartProducts  = this.sessionStorage.retrieve("cartProducts");

    if (cartProducts === null || cartProducts.length === 0) {
      cartProducts = [];
      cartProducts.push(this.product);
    }
    else {
      let isInCart = false;
      for (let i = 0;i < cartProducts.length; i++) {
        if (cartProducts[i].id === this.product.id) {
          isInCart = true;
        }
      }

      if (isInCart) {
        this.jhiAlertService.warning('onlyOneProduct');
        return;
      }
      else {
        for (let i = 0;i < this.cartProducts.length; i++) {
          if (this.cartProducts[i].houseFarm.id !== this.product.houseFarm.id) {
            this.jhiAlertService.info('onlyFromSameHouseFarm');
            return;
          }
        }

        cartProducts.push(this.product);
      }
    }

    this.sessionStorage.store("cartProducts", cartProducts);
    this.eventService.addItemToCart.emit();
  }
}
