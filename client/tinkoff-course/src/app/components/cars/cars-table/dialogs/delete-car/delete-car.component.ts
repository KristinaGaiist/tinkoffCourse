import {Component, Inject} from '@angular/core';
import {Car} from "../../../../../models/car";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CarService} from "../../../../../client/car.service";

export interface IDeleteCarDialogData {
  car: Car;
}

@Component({
  selector: 'app-delete-car',
  templateUrl: './delete-car.component.html',
  styleUrls: ['./delete-car.component.css']
})
export class DeleteCarComponent {

  constructor(public dialogRef: MatDialogRef<DeleteCarComponent>,
              @Inject(MAT_DIALOG_DATA) public data: IDeleteCarDialogData,
              public carService: CarService) {
  }

  async delete() {
    await this.carService.delete(this.data.car);
    this.dialogRef.close();
  }

  cancel() {
    this.dialogRef.close();
  }
}
