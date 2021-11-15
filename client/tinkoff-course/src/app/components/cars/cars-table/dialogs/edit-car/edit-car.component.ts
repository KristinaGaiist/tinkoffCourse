import {Component, Inject, OnInit} from '@angular/core';
import {Car} from "../../../../../models/car";
import {Model} from "../../../../../models/model";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ModelService} from "../../../../../client/model.service";
import {CarService} from "../../../../../client/car.service";

export interface IEditCarData {
  car: Car;
  save: (car: Car) => Promise<void>;
}

@Component({
  selector: 'app-edit-car',
  templateUrl: './edit-car.component.html',
  styleUrls: ['./edit-car.component.css']
})
export class EditCarComponent implements OnInit {
  models: Model[] = [];

  constructor(public dialogRef: MatDialogRef<EditCarComponent>,
              @Inject(MAT_DIALOG_DATA) public data: IEditCarData,
              public modelService: ModelService,
              public carService: CarService) {
  }

  ngOnInit(): void {
    this.loadModels();
  }

  async loadModels() {
    this.models = await this.modelService.getAll();
  }

  async save() {
    await this.data.save(this.data.car);
    this.dialogRef.close();
  }

  cancel() {
    this.dialogRef.close();
  }
}
