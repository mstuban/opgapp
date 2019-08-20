/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { HouseFarmService } from 'app/entities/house-farm/house-farm.service';
import { IHouseFarm, HouseFarm } from 'app/shared/model/house-farm.model';

describe('Service Tests', () => {
  describe('HouseFarm Service', () => {
    let injector: TestBed;
    let service: HouseFarmService;
    let httpMock: HttpTestingController;
    let elemDefault: IHouseFarm;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(HouseFarmService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new HouseFarm(0, 'AAAAAAA', false, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateFounded: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a HouseFarm', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateFounded: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateFounded: currentDate
          },
          returnedFromService
        );
        service
          .create(new HouseFarm(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a HouseFarm', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            hasLicense: true,
            dateFounded: currentDate.format(DATE_FORMAT),
            contactNumber: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateFounded: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of HouseFarm', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            hasLicense: true,
            dateFounded: currentDate.format(DATE_FORMAT),
            contactNumber: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateFounded: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a HouseFarm', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
