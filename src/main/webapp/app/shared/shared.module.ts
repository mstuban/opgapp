import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { OpgappSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [OpgappSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [OpgappSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OpgappSharedModule {
  static forRoot() {
    return {
      ngModule: OpgappSharedModule
    };
  }
}
