import {Component, Inject, OnInit} from '@angular/core';
import {Model} from "../../../../../../models/model";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ModelService} from "../../../../../../client/model.service";
import {Brand} from "../../../../../../models/brand";
import {BrandService} from "../../../../../../client/brand.service";

export interface IEditModelData {
  model: Model;
  save: (model: Model) => Promise<void>;
}

@Component({
  selector: 'app-edit-model',
  templateUrl: './edit-model.component.html',
  styleUrls: ['./edit-model.component.css']
})
export class EditModelComponent implements OnInit {
  brands: Brand[] = [];

  constructor(
      public dialogRef: MatDialogRef<EditModelComponent>,
      @Inject(MAT_DIALOG_DATA) public data: IEditModelData,
      public modelService: ModelService,
      public brandService: BrandService) {
  }

  ngOnInit(): void {
    this.loadBrands();
  }

  async save() {
    await this.data.save(this.data.model);
    this.dialogRef.close();
  }

  cancel() {
    this.dialogRef.close();
  }

  async loadBrands() {
    this.brands = await this.brandService.getAll();
  }
}
