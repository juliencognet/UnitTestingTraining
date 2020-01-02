export interface IProductInBasket {
  id?: number;
  quantity?: number;
  productId?: number;
  basketId?: number;
}

export class ProductInBasket implements IProductInBasket {
  constructor(public id?: number, public quantity?: number, public productId?: number, public basketId?: number) {}
}
