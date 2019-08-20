import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'house-farm',
        loadChildren: './house-farm/house-farm.module#OpgappHouseFarmModule'
      },
      {
        path: 'product',
        loadChildren: './product/product.module#OpgappProductModule'
      },
      {
        path: 'rating',
        loadChildren: './rating/rating.module#OpgappRatingModule'
      },
      {
        path: 'location',
        loadChildren: './location/location.module#OpgappLocationModule'
      },
      {
        path: 'region',
        loadChildren: './region/region.module#OpgappRegionModule'
      },
      {
        path: 'country',
        loadChildren: './country/country.module#OpgappCountryModule'
      },
      {
        path: 'order',
        loadChildren: './order/order.module#OpgappOrderModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OpgappEntityModule {}
