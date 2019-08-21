import { IProduct } from 'app/shared/model/product.model';
import {IUser} from "app/core";

export interface IRating {
  id?: number;
  stars?: number;
  comment?: string;
  product?: IProduct;
  user?: IUser;
}

export class Rating implements IRating {
  constructor(public id?: number, public stars?: number, public comment?: string, public product?: IProduct,
              public user?: IUser) {}
}
