import {Component, Inject} from '@angular/core';
import {City} from "../../../../../models/city";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CityService} from "../../../../../client/city.service";

export interface IEditCityData {
  city: City;
  save: (city: City) => Promise<void>;
}

@Component({
  selector: 'app-edit-city',
  templateUrl: './edit-city.component.html',
  styleUrls: ['./edit-city.component.css']
})
export class EditCityComponent {

  constructor(
      public dialogRef: MatDialogRef<EditCityComponent>,
      @Inject(MAT_DIALOG_DATA) public data: IEditCityData,
      public cityService: CityService) {
  }

  async save() {
    await this.data.save(this.data.city);
    this.dialogRef.close();
  }

  cancel() {
    this.dialogRef.close();
  }
}
