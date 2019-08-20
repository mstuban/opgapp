import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';
import { IHouseFarm } from 'app/shared/model/house-farm.model';

export const enum OrderStatus {
  IN_CREATION = 'IN_CREATION',
  PAID = 'PAID',
  IN_DELIVERY = 'IN_DELIVERY'
}

export interface IOrder {
  id?: number;
  number?: string;
  dateSubmitted?: Moment;
  totalPrice?: number;
  orderStatus?: OrderStatus;
  products?: IProduct[];
  houseFarm?: IHouseFarm;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public number?: string,
    public dateSubmitted?: Moment,
    public totalPrice?: number,
    public orderStatus?: OrderStatus,
    public products?: IProduct[],
    public houseFarm?: IHouseFarm
  ) {}
}
