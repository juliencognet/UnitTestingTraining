import { ProductInBasketService } from './../entities/product-in-basket/product-in-basket.service';
import { Component, OnInit } from '@angular/core';
import { IBasket } from 'app/shared/model/basket.model';
import { BasketService } from 'app/entities/basket/basket.service';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService, JhiAlert } from 'ng-jhipster';

@Component({
  selector: 'jhi-product-basket',
  templateUrl: './product-basket.component.html',
  styleUrls: ['./product-basket.component.scss']
})
export class ProductBasketComponent implements OnInit {
  currentBasket: IBasket = {};
  customerId = 0;
  loading = false;
  alerts: JhiAlert[] = [];

  constructor(
    private basketService: BasketService,
    private route: ActivatedRoute,
    private router: Router,
    private alertService: JhiAlertService,
    private productInBasketService: ProductInBasketService
  ) {}

  ngOnInit(): void {
    const basketId = Number(this.route.snapshot.paramMap.get('basketId'));
    this.customerId = Number(this.route.snapshot.paramMap.get('customerId'));
    this.loadBasket(basketId);
  }

  loadBasket(basketId: number): void {
    console.log('loading basket ' + basketId);
    this.loading = true;
    this.basketService.find(basketId).subscribe((res: HttpResponse<IBasket>) => {
      this.currentBasket = res.body ? res.body : {};
      this.loading = false;
    });
  }
  backToSearch(): void {
    this.router.navigate(['/productList', this.customerId]);
  }

  removeProduct(productInBasket: any): void {
    if (productInBasket != null) {
      this.productInBasketService
        .delete(productInBasket.id)
        .subscribe(() => this.loadBasket(this.currentBasket.id ? this.currentBasket.id : 0));
    }
  }

  pay(): void {
    this.alertService.error('Proceed to payment is not yet implemented', null);
  }
}
