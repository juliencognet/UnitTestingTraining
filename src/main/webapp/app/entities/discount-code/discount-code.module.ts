import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FicTestsAutomatisesSharedModule } from 'app/shared/shared.module';
import { DiscountCodeComponent } from './discount-code.component';
import { DiscountCodeDetailComponent } from './discount-code-detail.component';
import { DiscountCodeUpdateComponent } from './discount-code-update.component';
import { DiscountCodeDeleteDialogComponent } from './discount-code-delete-dialog.component';
import { discountCodeRoute } from './discount-code.route';

@NgModule({
  imports: [FicTestsAutomatisesSharedModule, RouterModule.forChild(discountCodeRoute)],
  declarations: [DiscountCodeComponent, DiscountCodeDetailComponent, DiscountCodeUpdateComponent, DiscountCodeDeleteDialogComponent],
  entryComponents: [DiscountCodeDeleteDialogComponent]
})
export class FicTestsAutomatisesDiscountCodeModule {}
