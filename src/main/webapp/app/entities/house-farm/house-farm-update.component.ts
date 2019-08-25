import {Component, OnInit} from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IHouseFarm, HouseFarm } from 'app/shared/model/house-farm.model';
import { HouseFarmService } from './house-farm.service';
import { ILocation } from 'app/shared/model/location.model';
import {AccountService} from 'app/core';
import {SERVER_API_URL} from "app/app.constants";

@Component({
  selector: 'jhi-house-farm-update',
  templateUrl: './house-farm-update.component.html'
})
export class HouseFarmUpdateComponent implements OnInit {
  isSaving: boolean;

  locations: ILocation[];
  currentAccount: any;
  apiUrl: string = SERVER_API_URL;
  image: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    hasLicense: [],
    dateFounded: [],
    contactNumber: [],
    streetAddress: [],
    postalCode: [],
    city: [],
    image: [],
    province: [],
    country: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected houseFarmService: HouseFarmService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected accountService: AccountService,
    protected router: Router
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ houseFarm }) => {
      this.updateForm(houseFarm);
    });
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
  }

  updateForm(houseFarm: IHouseFarm) {
    this.editForm.patchValue({
      id: houseFarm.id,
      name: houseFarm.name,
      hasLicense: houseFarm.hasLicense,
      dateFounded: houseFarm.dateFounded,
      contactNumber: houseFarm.contactNumber,
      streetAddress: houseFarm.streetAddress,
      postalCode: houseFarm.postalCode,
      city: houseFarm.city,
      province: houseFarm.province,
      country: houseFarm.country,
      image: this.image,
      user: this.currentAccount
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const houseFarm = this.createFromForm();
    if (houseFarm.id !== undefined) {
      this.subscribeToSaveResponse(this.houseFarmService.update(houseFarm));
    } else {
      this.subscribeToSaveResponse(this.houseFarmService.create(houseFarm));
    }
  }

  private createFromForm(): IHouseFarm {
    const entity = {
      ...new HouseFarm(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      hasLicense: this.editForm.get(['hasLicense']).value,
      dateFounded: this.editForm.get(['dateFounded']).value,
      contactNumber: this.editForm.get(['contactNumber']).value,
      streetAddress: this.editForm.get(['streetAddress']).value,
      postalCode: this.editForm.get(['postalCode']).value,
      city: this.editForm.get(['city']).value,
      province: this.editForm.get(['province']).value,
      country: this.editForm.get(['country']).value,
      image: this.image,
      user: this.currentAccount
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHouseFarm>>) {
    result.subscribe((res: HttpResponse<IHouseFarm>) => this.onSaveSuccess(res),
      (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess(res: HttpResponse<IHouseFarm>) {
    this.isSaving = false;
    this.router.navigate(['/house-farm/', res.body.id, 'view']);
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackLocationById(index: number, item: ILocation) {
    return item.id;
  }

  onUploadFinished(event) {
      this.image = event.serverResponse.response.body;
  }
}
