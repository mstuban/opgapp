import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRating, Rating } from 'app/shared/model/rating.model';
import { RatingService } from './rating.service';
import {IProduct, Product} from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import {AccountService} from "app/core";

@Component({
  selector: 'jhi-rating-update',
  templateUrl: './rating-update.component.html'
})
export class RatingUpdateComponent implements OnInit {
  isSaving: boolean;

  products: IProduct[];
  product: IProduct;
  currentAccount: any;

  editForm = this.fb.group({
    id: [],
    stars: [],
    comment: [],
    product: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ratingService: RatingService,
    protected productService: ProductService,
    private accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private route: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ rating }) => {
      this.updateForm(rating);
    });
    this.productService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduct[]>) => response.body)
      )
      .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));

    this.productService.find(this.route.snapshot.params['productId']).pipe(
      filter((response: HttpResponse<Product>) => response.ok),
      map((product: HttpResponse<Product>) => product.body)
    ).subscribe(
      product => {
        this.product = product;
      }
    );

    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
  }

  updateForm(rating: IRating) {
    this.editForm.patchValue({
      id: rating.id,
      stars: rating.stars,
      comment: rating.comment,
      product: rating.product
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const rating = this.createFromForm();
    if (rating.id !== undefined) {
      this.subscribeToSaveResponse(this.ratingService.update(rating));
    } else {
      this.subscribeToSaveResponse(this.ratingService.create(rating));
    }
  }

  private createFromForm(): IRating {
    const entity = {
      ...new Rating(),
      id: this.editForm.get(['id']).value,
      stars: this.editForm.get(['stars']).value,
      comment: this.editForm.get(['comment']).value,
      product: this.product,
      user: this.currentAccount
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRating>>) {
    result.subscribe((res: HttpResponse<IRating>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  onKeyDown() {
    return false;
  }

  trackProductById(index: number, item: IProduct) {
    return item.id;
  }
}
