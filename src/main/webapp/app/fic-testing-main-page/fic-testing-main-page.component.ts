import { ProductInBasket, IProductInBasket } from 'app/shared/model/product-in-basket.model';
import { ProductInBasketService } from 'app/entities/product-in-basket/product-in-basket.service';
import { IProduct } from 'app/shared/model/product.model';
import { IBasket, Basket } from 'app/shared/model/basket.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { BasketService } from 'app/entities/basket/basket.service';
import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import * as moment from 'moment';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-fic-testing-main-page',
  templateUrl: './fic-testing-main-page.component.html',
  styleUrls: ['./fic-testing-main-page.component.scss']
})
export class FicTestingMainPageComponent implements OnInit {
  basketFound = false;
  currentBasket: IBasket = {};
  currentCustomer: ICustomer = {};
  customerId = 0;

  constructor(
    private basketService: BasketService,
    private customerService: CustomerService,
    private productInBasketService: ProductInBasketService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.customerId = Number(this.route.snapshot.paramMap.get('customerId'));
    this.onSelectCustomer(this.customerId);
  }

  onSelectCustomer(customer: number): void {
    console.log('Selected customer: ' + customer);
    this.basketFound = false;

    // Get user and basket
    this.customerService.find(customer).subscribe(
      (res: HttpResponse<ICustomer>) => {
        this.currentCustomer = res.body ? res.body : {};
        this.retrieveBasketOrCreateIt(this.currentCustomer);
      },
      err => {
        console.error('Could not load customer ' + customer + ' : ' + err);
      }
    );
  }

  retrieveBasketOrCreateIt(customer: ICustomer): void {
    // If no basket, then create it
    if (customer.basketId == null) {
      const basket: Basket = new Basket();
      basket.customerId = customer.id;
      basket.totalPrice = 0;
      basket.creationDate = moment();
      this.basketService.create(basket).subscribe((res: HttpResponse<IBasket>) => {
        this.currentBasket = res.body ? res.body : {};
        console.log('Created a new empty basket with ID ' + this.currentBasket.id);
        this.basketFound = true;

        // Now must update user with this basket
        this.currentCustomer.basketId = this.currentBasket.id;
        this.customerService.update(this.currentCustomer).subscribe((res2: HttpResponse<ICustomer>) => {
          this.currentCustomer = res2.body ? res2.body : {};
          console.log('new basket of user is set to ' + this.currentCustomer.basketId);
        });
      });
    } else {
      this.getBasket(customer.basketId);
    }
  }

  getBasket(basketId: number): void {
    this.basketService.find(basketId).subscribe((res: HttpResponse<IBasket>) => {
      this.currentBasket = res.body ? res.body : {};
      console.log(this.currentBasket);
      this.basketFound = true;
    });
  }

  onSelectProduct(product: IProduct): void {
    const productInBasket = new ProductInBasket();
    productInBasket.basketId = this.currentBasket.id;
    productInBasket.productId = product.id;
    productInBasket.quantity = 1;

    this.productInBasketService.create(productInBasket).subscribe((res: HttpResponse<IProductInBasket>) => {
      const addedProductInBasket: ProductInBasket = res.body ? res.body : {};
      console.log('Added new row ' + addedProductInBasket.id);
      this.getBasket(this.currentBasket.id ? this.currentBasket.id : 0);
    });
  }
}
