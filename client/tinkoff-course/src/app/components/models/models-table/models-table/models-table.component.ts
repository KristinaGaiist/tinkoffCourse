import {Model} from './../../../../models/model';
import {EditModelComponent, IEditModelData} from './dialogs/edit-model/edit-model.component';
import {Component, OnInit} from '@angular/core';
import {ModelService} from "../../../../client/model.service";
import {MatDialog} from "@angular/material/dialog";
import {DeleteModelComponent} from "./dialogs/delete-model/delete-model.component";

@Component({
  selector: 'app-models-table',
  templateUrl: './models-table.component.html',
  styleUrls: ['./models-table.component.css']
})
export class ModelsTableComponent implements OnInit {
  displayedColumns: string[] = ["id", "model", "brand", "edit", "delete", "add"];
  models: Model[] = [];

  constructor(private readonly modelService: ModelService,
    private readonly matDialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadModels();
  }

  async loadModels() {
    this.models = await this.modelService.getAll();
  }

  addNew() {
    this.openEditDialog({
      model: new Model(),
      save: (model) => this.modelService.add(model)
    });
  }

  edit(model: Model) {
    this.openEditDialog({
      model,
      save: (model) => this.modelService.update(model)
    });
  }

  openEditDialog(data: IEditModelData) {
    this.matDialog
      .open(EditModelComponent, { data })
      .afterClosed()
      .subscribe(() => this.loadModels());
  }

  delete(model: Model) {
    this.matDialog
      .open(DeleteModelComponent, { data: { model } })
      .afterClosed()
      .subscribe(() => this.loadModels());
  }
}
