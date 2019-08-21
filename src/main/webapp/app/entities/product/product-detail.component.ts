import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {IProduct} from 'app/shared/model/product.model';
import {AccountService} from "app/core";

@Component({
  selector: 'jhi-product-detail',
  templateUrl: './product-detail.component.html'
})
export class ProductDetailComponent implements OnInit {
  product: IProduct;
  currentAccount: any;

  constructor(protected activatedRoute: ActivatedRoute,
              protected accountService: AccountService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.product = product;
    });

    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
  }

  previousState() {
    window.history.back();
  }

}
