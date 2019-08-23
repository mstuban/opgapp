import { IRating } from 'app/shared/model/rating.model';
import { IHouseFarm } from 'app/shared/model/house-farm.model';
import { IOrder } from 'app/shared/model/order.model';

export const enum ProductType {
  MEAT = 'MEAT',
  CHEESE = 'CHEESE',
  DAIRY = 'DAIRY',
  SEAFOOD = 'SEAFOOD',
  HONEY_AND_JAM = 'HONEY_AND_JAM',
  PASTA_AND_FLOUR = 'PASTA_AND_FLOUR',
  OIL_AND_VINEGAR = 'OIL_AND_VINEGAR',
  BREAD_AND_GRAINS = 'BREAD_AND_GRAINS',
  FRUIT_AND_VEGETABLES = 'FRUIT_AND_VEGETABLES',
  NON_ALCOHOLIC_DRINKS = 'NON_ALCOHOLIC_DRINKS',
  ALCOHOLIC_DRINKS = 'ALCOHOLIC_DRINKS',
  SWEETS = 'SWEETS',
  HERBS_TEAS_AND_SPICES = 'HERBS_TEAS_AND_SPICES',
  GIFT_AND_SOUVENIRS = 'GIFT_AND_SOUVENIRS'
}

export interface IProduct {
  id?: number;
  name?: string;
  imageUrl?: string;
  price?: number;
  availableAmountInLiters?: number;
  averageRating?: number;
  availableAmountInKilograms?: number;
  isAvailable?: boolean;
  productType?: ProductType;
  ratings?: IRating[];
  houseFarm?: IHouseFarm;
  order?: IOrder;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public imageUrl?: string,
    public price?: number,
    public availableAmountInLiters?: number,
    public availableAmountInKilograms?: number,
    public isAvailable?: boolean,
    public productType?: ProductType,
    public ratings?: IRating[],
    public houseFarm?: IHouseFarm,
    public order?: IOrder
  ) {
    this.isAvailable = this.isAvailable || false;
  }
}
