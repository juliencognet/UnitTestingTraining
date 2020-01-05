import { Component, OnInit } from '@angular/core';

import { HttpResponse } from '@angular/common/http';
import { ICustomer } from '../../app/shared/model/customer.model';
import { CustomerService } from '../entities/customer/customer.service';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-user-selector',
  templateUrl: './user-selector.component.html',
  styleUrls: ['./user-selector.component.scss']
})
export class UserSelectorComponent implements OnInit {
  customers?: ICustomer[];
  loading = false;
  selectedCustomer = 0;

  constructor(protected customerService: CustomerService, private router: Router) {}

  ngOnInit(): void {
    this.loadListOfCustomers();
  }

  loadListOfCustomers(): void {
    this.loading = true;
    this.customerService.query().subscribe(
      (res: HttpResponse<ICustomer[]>) => {
        // console.info("Got results");
        this.customers = res.body ? res.body : [];
        this.loading = false;
      },
      err => {
        console.error('An error occurred: ' + err);
        this.loading = false;
      }
    );
  }

  onChange(newValue: number): void {
    console.log('Loading product List of ' + newValue);
    this.router.navigate(['/productList/', newValue]);
  }
}
