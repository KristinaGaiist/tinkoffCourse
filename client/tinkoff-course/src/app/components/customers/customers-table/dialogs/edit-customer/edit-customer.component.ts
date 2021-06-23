import {Component, Inject, OnInit} from '@angular/core';
import {Customer} from "../../../../../models/customer";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CustomerService} from "../../../../../client/customer.service";
import {CityService} from "../../../../../client/city.service";
import {City} from "../../../../../models/city";

export interface IEditCustomerData {
  customer: Customer;
  save: (customer: Customer) => Promise<void>;
}

@Component({
  selector: 'app-edit-customer',
  templateUrl: './edit-customer.component.html',
  styleUrls: ['./edit-customer.component.css']
})
export class EditCustomerComponent implements OnInit {
  cities: City[] = [];

  constructor(public dialogRef: MatDialogRef<EditCustomerComponent>,
              @Inject(MAT_DIALOG_DATA) public data: IEditCustomerData,
              public cityService: CityService,
              public customerService: CustomerService) {
  }

  ngOnInit(): void {
    this.loadCities();
  }

  async loadCities() {
    this.cities = await this.cityService.getAll();
  }

  async save() {
    await this.data.save(this.data.customer);
    this.dialogRef.close();
  }

  cancel() {
    this.dialogRef.close();
  }
}
