import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {IProduct} from 'app/shared/model/product.model';
import {AccountService} from "app/core";
import {IRating} from "app/shared/model/rating.model";

@Component({
  selector: 'jhi-product-detail',
  templateUrl: './product-detail.component.html'
})
export class ProductDetailComponent implements OnInit {
  product: IProduct;
  currentAccount: any;
  averageRating: number;

  constructor(protected activatedRoute: ActivatedRoute,
              protected accountService: AccountService) {}

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
  }

  previousState() {
    window.history.back();
  }

}
