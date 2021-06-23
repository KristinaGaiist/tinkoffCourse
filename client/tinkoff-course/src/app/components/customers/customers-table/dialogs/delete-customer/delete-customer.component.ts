import {Component, Inject} from '@angular/core';
import {Customer} from "../../../../../models/customer";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CustomerService} from "../../../../../client/customer.service";

export interface IDeleteCustomerDialogData {
  customer: Customer;
}

@Component({
  selector: 'app-delete-customer',
  templateUrl: './delete-customer.component.html',
  styleUrls: ['./delete-customer.component.css']
})
export class DeleteCustomerComponent {

  constructor(public dialogRef: MatDialogRef<DeleteCustomerComponent>,
              @Inject(MAT_DIALOG_DATA) public data: IDeleteCustomerDialogData,
              public customerService: CustomerService) {
  }

  async delete() {
    await this.customerService.delete(this.data.customer);
    this.dialogRef.close();
  }

  cancel() {
    this.dialogRef.close();
  }
}
