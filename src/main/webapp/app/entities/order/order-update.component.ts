import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { IHouseFarm } from 'app/shared/model/house-farm.model';
import { HouseFarmService } from 'app/entities/house-farm';

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html'
})
export class OrderUpdateComponent implements OnInit {
  isSaving: boolean;

  housefarms: IHouseFarm[];
  dateSubmittedDp: any;

  editForm = this.fb.group({
    id: [],
    number: [],
    dateSubmitted: [],
    totalPrice: [],
    orderStatus: [],
    houseFarm: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected orderService: OrderService,
    protected houseFarmService: HouseFarmService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ order }) => {
      this.updateForm(order);
    });
    this.houseFarmService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IHouseFarm[]>) => mayBeOk.ok),
        map((response: HttpResponse<IHouseFarm[]>) => response.body)
      )
      .subscribe((res: IHouseFarm[]) => (this.housefarms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(order: IOrder) {
    this.editForm.patchValue({
      id: order.id,
      number: order.number,
      dateSubmitted: order.dateSubmitted,
      totalPrice: order.totalPrice,
      orderStatus: order.orderStatus,
      houseFarm: order.houseFarm
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    const entity = {
      ...new Order(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      dateSubmitted: this.editForm.get(['dateSubmitted']).value,
      totalPrice: this.editForm.get(['totalPrice']).value,
      orderStatus: this.editForm.get(['orderStatus']).value,
      houseFarm: this.editForm.get(['houseFarm']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>) {
    result.subscribe((res: HttpResponse<IOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
