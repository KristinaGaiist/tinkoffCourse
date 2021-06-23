import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {Customer} from "../../../models/customer";
import {CustomerService} from "../../../client/customer.service";
import {DeleteCustomerComponent} from "./dialogs/delete-customer/delete-customer.component";
import {EditCustomerComponent, IEditCustomerData} from "./dialogs/edit-customer/edit-customer.component";

@Component({
  selector: 'app-customers-table',
  templateUrl: './customers-table.component.html',
  styleUrls: ['./customers-table.component.css']
})
export class CustomersTableComponent implements OnInit {
  displayedColumns: string[] = ["id", "lastName", "firstName", "middleName", "city", "edit", "delete", "add"];
  customers: Customer[] = [];

  constructor(private readonly customerService: CustomerService,
              private readonly matDialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadCustomers();
  }

  async loadCustomers() {
    this.customers = await this.customerService.getAll();
  }

  addNew() {
    this.openEditDialog({
      customer: new Customer(),
      save: (customer) => this.customerService.add(customer)
    });
  }

  edit(customer: Customer) {
    this.openEditDialog({
      customer,
      save: (customer) => this.customerService.update(customer)
    });
  }

  openEditDialog(data: IEditCustomerData) {
    this.matDialog
    .open(EditCustomerComponent, {data})
    .afterClosed()
    .subscribe(() => this.loadCustomers());
  }

  delete(customer: Customer) {
    this.matDialog
    .open(DeleteCustomerComponent, {data: {customer}})
    .afterClosed()
    .subscribe(() => this.loadCustomers());
  }
}
