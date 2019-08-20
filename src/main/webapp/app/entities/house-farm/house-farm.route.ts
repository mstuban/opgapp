import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HouseFarm } from 'app/shared/model/house-farm.model';
import { HouseFarmService } from './house-farm.service';
import { HouseFarmComponent } from './house-farm.component';
import { HouseFarmDetailComponent } from './house-farm-detail.component';
import { HouseFarmUpdateComponent } from './house-farm-update.component';
import { HouseFarmDeletePopupComponent } from './house-farm-delete-dialog.component';
import { IHouseFarm } from 'app/shared/model/house-farm.model';

@Injectable({ providedIn: 'root' })
export class HouseFarmResolve implements Resolve<IHouseFarm> {
  constructor(private service: HouseFarmService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHouseFarm> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<HouseFarm>) => response.ok),
        map((houseFarm: HttpResponse<HouseFarm>) => houseFarm.body)
      );
    }
    return of(new HouseFarm());
  }
}

export const houseFarmRoute: Routes = [
  {
    path: '',
    component: HouseFarmComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'opgappApp.houseFarm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HouseFarmDetailComponent,
    resolve: {
      houseFarm: HouseFarmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'opgappApp.houseFarm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HouseFarmUpdateComponent,
    resolve: {
      houseFarm: HouseFarmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'opgappApp.houseFarm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HouseFarmUpdateComponent,
    resolve: {
      houseFarm: HouseFarmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'opgappApp.houseFarm.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const houseFarmPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: HouseFarmDeletePopupComponent,
    resolve: {
      houseFarm: HouseFarmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'opgappApp.houseFarm.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
