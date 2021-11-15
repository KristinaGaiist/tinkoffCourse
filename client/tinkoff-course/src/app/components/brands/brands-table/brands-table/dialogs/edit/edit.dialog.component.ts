import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Component, Inject} from '@angular/core';
import {BrandService} from 'src/app/client/brand.service';
import {Brand} from 'src/app/models/brand';

export interface IEditDialogData {
  brand: Brand;
  save: (brand: Brand) => Promise<void>;
}

@Component({
  selector: 'app-edit.dialog',
  templateUrl: './edit.dialog.html',
  styleUrls: ['./edit.dialog.css']
})
export class EditDialogComponent {
  constructor(
      public dialogRef: MatDialogRef<EditDialogComponent>,
      @Inject(MAT_DIALOG_DATA) public data: IEditDialogData,
      public brandsService: BrandService) {
  }

  async save() {
    await this.data.save(this.data.brand);
    this.dialogRef.close();
  }

  cancel() {
    this.dialogRef.close();
  }
}
