import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import { LoginModalService, AccountService, Account } from 'app/core';
import {filter, map} from "rxjs/operators";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {IHouseFarm} from "app/shared/model/house-farm.model";
import {HouseFarmService} from "app/entities/house-farm";

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
  account: any;
  modalRef: NgbModalRef;
  houseFarms: IHouseFarm[];

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private houseFarmService: HouseFarmService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager
  ) {}

  loadAll() {
    this.houseFarmService
      .query()
      .pipe(
        filter((res: HttpResponse<IHouseFarm[]>) => res.ok),
        map((res: HttpResponse<IHouseFarm[]>) => res.body)
      )
      .subscribe(
        (res: IHouseFarm[]) => {
          this.houseFarms = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.account = account;
    });
    this.registerAuthenticationSuccess();
  }

  registerAuthenticationSuccess() {
    this.eventManager.subscribe('authenticationSuccess', message => {
      this.accountService.identity().then(account => {
        this.account = account;
      });
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }
}
