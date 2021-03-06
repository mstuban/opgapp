import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OpgappSharedModule } from 'app/shared';
import {
  HouseFarmComponent,
  HouseFarmDetailComponent,
  HouseFarmUpdateComponent,
  HouseFarmDeletePopupComponent,
  HouseFarmDeleteDialogComponent,
  houseFarmRoute,
  houseFarmPopupRoute
} from './';
import {ImageUploadModule} from 'angular2-image-upload';

const ENTITY_STATES = [...houseFarmRoute, ...houseFarmPopupRoute];

@NgModule({
  imports: [OpgappSharedModule, RouterModule.forChild(ENTITY_STATES), ImageUploadModule.forRoot()],
  declarations: [
    HouseFarmComponent,
    HouseFarmDetailComponent,
    HouseFarmUpdateComponent,
    HouseFarmDeleteDialogComponent,
    HouseFarmDeletePopupComponent
  ],
  entryComponents: [HouseFarmComponent, HouseFarmUpdateComponent, HouseFarmDeleteDialogComponent, HouseFarmDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OpgappHouseFarmModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
