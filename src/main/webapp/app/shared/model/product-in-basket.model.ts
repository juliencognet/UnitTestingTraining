import { IProduct } from './product.model';

export interface IProductInBasket {
  id?: number;
  quantity?: number;
  productId?: number;
  product?: IProduct;
  basketId?: number;
}

export class ProductInBasket implements IProductInBasket {
  constructor(public id?: number, public quantity?: number, public productId?: number, public basketId?: number) {}
}
