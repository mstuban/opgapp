import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { Router } from '@angular/router';
import {NgbDropdown, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiLanguageService} from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { JhiLanguageHelper, AccountService, LoginModalService, LoginService } from 'app/core';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import {OrderService} from 'app/entities/order';
import {IProduct} from "app/shared/model/product.model";
import {EventService} from "app/core/event/event.service";

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['navbar.scss']
})
export class NavbarComponent implements OnInit {
  inProduction: boolean;
  isNavbarCollapsed: boolean;
  languages: any[];
  swaggerEnabled: boolean;
  modalRef: NgbModalRef;
  version: string;
  cartProducts: IProduct[];
  currentAccount: any;

  @ViewChild("cartDropdown", {static: false}) cartDropdown: NgbDropdown;

  constructor(
    private loginService: LoginService,
    private languageService: JhiLanguageService,
    private languageHelper: JhiLanguageHelper,
    private sessionStorage: SessionStorageService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private profileService: ProfileService,
    private router: Router,
    private orderService: OrderService,
    private jhiAlertService: JhiAlertService,
    private eventService: EventService
  ) {
    this.version = VERSION ? 'v' + VERSION : '';
    this.isNavbarCollapsed = true;
  }

  ngOnInit() {
    this.languageHelper.getAll().then(languages => {
      this.languages = languages;
    });

    this.profileService.getProfileInfo().then(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });

    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });

    this.cartProducts = this.sessionStorage.retrieve("cartProducts");

    if (this.cartProducts === null) {
      this.cartProducts = [];
    }

    this.eventService.addItemToCart.subscribe(
      () => {
        this.cartProducts = this.sessionStorage.retrieve("cartProducts");

        if (!this.cartDropdown.isOpen()) {
          this.cartDropdown.open();
        }
      }
    );

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

  changeLanguage(languageKey: string) {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  collapseNavbar() {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  logout() {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar() {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getImageUrl() {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : null;
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
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
