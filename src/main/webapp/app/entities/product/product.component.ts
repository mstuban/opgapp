import {Component, OnInit, OnDestroy, ViewChild, ElementRef} from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AccountService } from 'app/core';
import { ProductService } from './product.service';
import {IProduct} from "app/shared/model/product.model";

@Component({
  selector: 'jhi-product',
  templateUrl: './product.component.html'
})
export class ProductComponent implements OnInit, OnDestroy {
  products: IProduct[];
  currentAccount: any;
  eventSubscriber: Subscription;
  isAvailableInput: boolean = true;
  searchCriteria: any;

  @ViewChild("nameFilterInput", {static: false}) nameFilterInput: ElementRef;
  @ViewChild("productTypeInput", {static: false}) productTypeInput: ElementRef;

  constructor(
    protected productService: ProductService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
      this.productService
        .query()
        .pipe(
          filter((res: HttpResponse<IProduct[]>) => res.ok),
          map((res: HttpResponse<IProduct[]>) => res.body)
        )
        .subscribe(
          (res: IProduct[]) => {
            this.products = res;
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        )
  }

  loadByCriteria() {
      this.productService
        .query(this.searchCriteria)
        .pipe(
          filter((res: HttpResponse<IProduct[]>) => res.ok),
          map((res: HttpResponse<IProduct[]>) => res.body)
        )
        .subscribe(
          (res: IProduct[]) => {
            this.products = res;
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        )
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProducts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProduct) {
    return item.id;
  }

  registerChangeInProducts() {
    this.eventSubscriber = this.eventManager.subscribe('productListModification', response => this.loadAll());
  }

  filterProducts() {
    this.searchCriteria = {
      'name.contains': this.nameFilterInput.nativeElement.value,
      'isAvailable.equals': this.isAvailableInput,
      'productType.equals': this.productTypeInput.nativeElement.value
    };
    this.loadByCriteria();
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  resetFilters() {
    this.loadAll();
    this.productTypeInput.nativeElement.value = '';
    this.nameFilterInput.nativeElement.value = '';
  }
}
