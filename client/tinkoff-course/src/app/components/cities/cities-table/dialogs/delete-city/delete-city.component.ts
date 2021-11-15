import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CityService} from "../../../../../client/city.service";
import {City} from "../../../../../models/city";

export interface IDeleteCityData {
  city: City;
}

@Component({
  selector: 'app-delete-city',
  templateUrl: './delete-city.component.html',
  styleUrls: ['./delete-city.component.css']
})
export class DeleteCityComponent {

  constructor(
      public dialogRef: MatDialogRef<DeleteCityComponent>,
      @Inject(MAT_DIALOG_DATA) public data: IDeleteCityData,
      public cityService: CityService) {
  }

  async delete() {
    await this.cityService.delete(this.data.city);
    this.dialogRef.close()
  }

  cancel() {
    this.dialogRef.close()
  }
}
