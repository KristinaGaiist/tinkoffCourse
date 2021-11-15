import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Model} from "../../../../../../models/model";
import {ModelService} from "../../../../../../client/model.service";

export interface IDeleteModelDialogData {
  model: Model;
}

@Component({
  selector: 'app-delete-model',
  templateUrl: './delete-model.component.html',
  styleUrls: ['./delete-model.component.css']
})
export class DeleteModelComponent {

  constructor(
      public dialogRef: MatDialogRef<DeleteModelComponent>,
      @Inject(MAT_DIALOG_DATA) public data: IDeleteModelDialogData,
      public modelService: ModelService) {
  }

  async delete() {
    await this.modelService.delete(this.data.model);
    this.dialogRef.close();
  }

  cancel() {
    this.dialogRef.close();
  }
}
