import {Moment} from 'moment';
import {IProduct} from 'app/shared/model/product.model';
import {IOrder} from 'app/shared/model/order.model';
import {IUser} from 'app/core';
import {IImage} from "app/shared/model/image.model";

export interface IHouseFarm {
  id?: number;
  name?: string;
  hasLicense?: boolean;
  dateFounded?: Moment;
  contactNumber?: string;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  province?: string;
  country?: string;
  products?: IProduct[];
  orders?: IOrder[];
  user?: IUser;
  image?: IImage
}

export class HouseFarm implements IHouseFarm {
  constructor(
    public id?: number,
    public name?: string,
    public hasLicense?: boolean,
    public dateFounded?: Moment,
    public contactNumber?: string,
    public streetAddress?: string,
    public postalCode?: string,
    public city?: string,
    public province?: string,
    public country?: string,
    public products?: IProduct[],
    public image?: IImage,
    public orders?: IOrder[],
    public user?: IUser
  ) {
    this
      .hasLicense = this.hasLicense || false;
  }
}
