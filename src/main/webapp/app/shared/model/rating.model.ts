import { IProduct } from 'app/shared/model/product.model';

export interface IRating {
  id?: number;
  stars?: number;
  comment?: string;
  product?: IProduct;
}

export class Rating implements IRating {
  constructor(public id?: number, public stars?: number, public comment?: string, public product?: IProduct) {}
}
