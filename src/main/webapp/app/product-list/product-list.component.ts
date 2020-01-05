import { ProductService } from 'app/entities/product/product.service';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { IProduct } from 'app/shared/model/product.model';
import { HttpResponse } from '@angular/common/http';
import { JhiAlert, JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {
  alerts: JhiAlert[] = [];
  products?: IProduct[];
  loading = true;

  @Output() onSelectProduct = new EventEmitter<IProduct>();

  constructor(private productService: ProductService, private alertService: JhiAlertService) {}

  ngOnInit(): void {
    this.loadProducts();
  }
  loadProducts(): void {
    this.loading = true;
    this.productService.query({ size: 1000 }).subscribe(
      (res: HttpResponse<IProduct[]>) => {
        this.products = res.body ? res.body : [];
        this.loading = false;
      },
      err => console.error('Unexpected error while loading products : ' + err)
    );
  }
  selectProduct(product: IProduct): void {
    const newAlert: JhiAlert = {
      type: 'danger',
      msg: 'New product added',
      timeout: 5000,
      toast: true,
      scoped: true
    };

    this.alerts.push(this.alertService.addAlert(newAlert, this.alerts));
    console.log('selected ' + product.productName);
    this.onSelectProduct.emit(product);
  }
}
