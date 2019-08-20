import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHouseFarm } from 'app/shared/model/house-farm.model';

type EntityResponseType = HttpResponse<IHouseFarm>;
type EntityArrayResponseType = HttpResponse<IHouseFarm[]>;

@Injectable({ providedIn: 'root' })
export class HouseFarmService {
  public resourceUrl = SERVER_API_URL + 'api/house-farms';

  constructor(protected http: HttpClient) {}

  create(houseFarm: IHouseFarm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(houseFarm);
    return this.http
      .post<IHouseFarm>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(houseFarm: IHouseFarm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(houseFarm);
    return this.http
      .put<IHouseFarm>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHouseFarm>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHouseFarm[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(houseFarm: IHouseFarm): IHouseFarm {
    const copy: IHouseFarm = Object.assign({}, houseFarm, {
      dateFounded: houseFarm.dateFounded != null && houseFarm.dateFounded.isValid() ? houseFarm.dateFounded.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateFounded = res.body.dateFounded != null ? moment(res.body.dateFounded) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((houseFarm: IHouseFarm) => {
        houseFarm.dateFounded = houseFarm.dateFounded != null ? moment(houseFarm.dateFounded) : null;
      });
    }
    return res;
  }
}
