import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.FicTestsAutomatisesProductModule)
      },
      {
        path: 'discount-code',
        loadChildren: () => import('./discount-code/discount-code.module').then(m => m.FicTestsAutomatisesDiscountCodeModule)
      },
      {
        path: 'basket',
        loadChildren: () => import('./basket/basket.module').then(m => m.FicTestsAutomatisesBasketModule)
      },
      {
        path: 'product-in-basket',
        loadChildren: () => import('./product-in-basket/product-in-basket.module').then(m => m.FicTestsAutomatisesProductInBasketModule)
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.FicTestsAutomatisesCustomerModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class FicTestsAutomatisesEntityModule {}
