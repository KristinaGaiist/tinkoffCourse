import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Component, Inject} from '@angular/core';
import {BrandService} from 'src/app/client/brand.service';
import {Brand} from 'src/app/models/brand';

export interface IDeleteDialogData {
  brand: Brand;
}

@Component({
  selector: 'app-delete.dialog',
  templateUrl: '../../dialogs/delete/delete.dialog.html',
  styleUrls: ['../../dialogs/delete/delete.dialog.css']
})
export class DeleteDialogComponent {

  constructor(
      public dialogRef: MatDialogRef<DeleteDialogComponent>,
      @Inject(MAT_DIALOG_DATA) public data: IDeleteDialogData,
      public brandService: BrandService) {
  }


  async delete() {
    await this.brandService.delete(this.data.brand);
    this.dialogRef.close()
  }

  cancel() {
    this.dialogRef.close();
  }
}
