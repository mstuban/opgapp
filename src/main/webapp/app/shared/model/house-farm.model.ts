import { Moment } from 'moment';
import { ILocation } from 'app/shared/model/location.model';
import { IProduct } from 'app/shared/model/product.model';
import { IOrder } from 'app/shared/model/order.model';

export interface IHouseFarm {
  id?: number;
  name?: string;
  hasLicense?: boolean;
  dateFounded?: Moment;
  contactNumber?: string;
  location?: ILocation;
  products?: IProduct[];
  orders?: IOrder[];
}

export class HouseFarm implements IHouseFarm {
  constructor(
    public id?: number,
    public name?: string,
    public hasLicense?: boolean,
    public dateFounded?: Moment,
    public contactNumber?: string,
    public location?: ILocation,
    public products?: IProduct[],
    public orders?: IOrder[]
  ) {
    this.hasLicense = this.hasLicense || false;
  }
}
