import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OpgappSharedModule } from 'app/shared';
import {
  ProductComponent,
  ProductDetailComponent,
  ProductUpdateComponent,
  ProductDeletePopupComponent,
  ProductDeleteDialogComponent,
  productRoute,
  productPopupRoute
} from './';
import {ImageUploadModule} from "angular2-image-upload";

const ENTITY_STATES = [...productRoute, ...productPopupRoute];

@NgModule({
  imports: [OpgappSharedModule, RouterModule.forChild(ENTITY_STATES), ImageUploadModule.forRoot()],
  declarations: [
    ProductComponent,
    ProductDetailComponent,
    ProductUpdateComponent,
    ProductDeleteDialogComponent,
    ProductDeletePopupComponent
  ],
  entryComponents: [ProductComponent, ProductUpdateComponent, ProductDeleteDialogComponent, ProductDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OpgappProductModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
