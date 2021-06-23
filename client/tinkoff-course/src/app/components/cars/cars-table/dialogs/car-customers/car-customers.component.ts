import {Component, Inject, OnInit} from '@angular/core';
import {Car} from "../../../../../models/car";
import {Customer} from "../../../../../models/customer";
import {CarService} from "../../../../../client/car.service";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {CustomerService} from "../../../../../client/customer.service";
import {ArrayUtils} from "../../../../../utils/array-utils";
import {FormControl} from "@angular/forms";
import {StringUtils} from "../../../../../utils/string-utils";

export interface ICarCustomersData {
  car: Car
}

@Component({
  selector: 'app-car-customers',
  templateUrl: './car-customers.component.html',
  styleUrls: ['./car-customers.component.css']
})
export class CarCustomersComponent implements OnInit {
  formControl = new FormControl();
  carCustomers: Customer[] = [];

  customers: Customer[] = [];
  filteredCustomers: Customer[] = [];

  constructor(@Inject(MAT_DIALOG_DATA) public data: ICarCustomersData,
              private readonly carService: CarService,
              private readonly customerService: CustomerService) {
  }

  ngOnInit(): void {
    this.loadAllCustomers()
    .then(() => {
      this.loadCarCustomers();
    });

    this.filterCustomers();
  }

  private async loadAllCustomers() {
    this.customers = await this.customerService.getAll();
  }

  private async loadCarCustomers() {
    const carCustomers = await this.carService.getCustomers(this.data.car);
    const carCustomersIds = carCustomers.map(c => c.id);

    this.carCustomers = this.customers
    .filter(c => ArrayUtils.contains(carCustomersIds, c.id));
  }

  private filterCustomers() {
    this.formControl.valueChanges
    .subscribe(filter => {
      this.filteredCustomers = this.customers
      .filter(c => StringUtils.contains(this.display(c), filter));
    })
  }

  display(customer: Customer): string {
    return `${customer.lastName} ${customer.firstName} ${customer.middleName}`;
  }

  async delete(customer: Customer) {
    await this.carService.deleteCustomer(this.data.car, customer);
    await this.loadCarCustomers();
  }

  async addCustomer() {
    const customer = this.formControl.value as Customer;
    await this.carService.addCustomer(this.data.car, customer);
    await this.loadCarCustomers();
  }
}
