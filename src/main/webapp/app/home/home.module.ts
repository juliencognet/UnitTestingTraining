import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FicTestsAutomatisesSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { FicTestingMainPageComponent } from '../fic-testing-main-page/fic-testing-main-page.component';
import { ProductListComponent } from '../product-list/product-list.component';
import { ProductBasketComponent } from '../product-basket/product-basket.component';
import { UserSelectorComponent } from '../user-selector/user-selector.component';
import { BasketIconComponent } from '../basket-icon/basket-icon.component';

@NgModule({
  imports: [FicTestsAutomatisesSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [
    HomeComponent,
    FicTestingMainPageComponent,
    ProductListComponent,
    ProductBasketComponent,
    UserSelectorComponent,
    BasketIconComponent
  ]
})
export class FicTestsAutomatisesHomeModule {}
