import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import {HouseFarm, IHouseFarm} from 'app/shared/model/house-farm.model';
import { HouseFarmService } from 'app/entities/house-farm';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
  isSaving: boolean;

  housefarms: IHouseFarm[];

  houseFarm: IHouseFarm;

  orders: IOrder[];

  editForm = this.fb.group({
    id: [],
    name: [],
    imageUrl: [],
    price: [],
    availableAmountInLiters: [],
    availableAmountInKilograms: [],
    isAvailable: [],
    productType: [],
    houseFarm: [],
    order: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productService: ProductService,
    protected houseFarmService: HouseFarmService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);
    });
    this.houseFarmService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IHouseFarm[]>) => mayBeOk.ok),
        map((response: HttpResponse<IHouseFarm[]>) => response.body)
      )
      .subscribe((res: IHouseFarm[]) => (this.housefarms = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.orderService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOrder[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOrder[]>) => response.body)
      )
      .subscribe((res: IOrder[]) => (this.orders = res), (res: HttpErrorResponse) => this.onError(res.message));

    this.houseFarmService.find(this.route.snapshot.params['houseFarmId']).pipe(
      filter((response: HttpResponse<HouseFarm>) => response.ok),
      map((product: HttpResponse<HouseFarm>) => product.body)
    ).subscribe(
      houseFarm => {
        this.houseFarm = houseFarm;
      }
    );

  }

  updateForm(product: IProduct) {
    this.editForm.patchValue({
      id: product.id,
      name: product.name,
      imageUrl: product.imageUrl,
      price: product.price,
      availableAmountInLiters: product.availableAmountInLiters,
      availableAmountInKilograms: product.availableAmountInKilograms,
      isAvailable: product.isAvailable,
      productType: product.productType,
      houseFarm: product.houseFarm,
      order: product.order
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    const entity = {
      ...new Product(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      imageUrl: this.editForm.get(['imageUrl']).value,
      price: this.editForm.get(['price']).value,
      availableAmountInLiters: this.editForm.get(['availableAmountInLiters']).value,
      availableAmountInKilograms: this.editForm.get(['availableAmountInKilograms']).value,
      isAvailable: this.editForm.get(['isAvailable']).value,
      productType: this.editForm.get(['productType']).value,
      houseFarm: this.houseFarm
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>) {
    result.subscribe((res: HttpResponse<IProduct>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackHouseFarmById(index: number, item: IHouseFarm) {
    return item.id;
  }

  trackOrderById(index: number, item: IOrder) {
    return item.id;
  }
}
