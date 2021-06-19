import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { BrandService } from "../../../../client/brand.service";
import { Brand } from "../../../../models/brand";
import { DeleteDialogComponent } from './dialogs/delete/delete.dialog.component';
import { EditDialogComponent, IEditDialogData } from './dialogs/edit/edit.dialog.component';

@Component({
  selector: 'app-brands-table',
  templateUrl: './brands-table.component.html',
  styleUrls: ['./brands-table.component.css']
})
export class BrandsTableComponent implements OnInit {
  displayedColumns: string[] = ["id", "name", "edit", "delete", "add"];
  brands: Brand[] = [];

  constructor(
    private readonly brandService: BrandService,
    private readonly matDialog: MatDialog) { }

  ngOnInit(): void {
    this.loadBrands();
  }

  async loadBrands() {
    this.brands = await this.brandService.getAll();
  }

  addNew() {
    this.openEditDialog({
      brand: new Brand(),
      save: (brand) => this.brandService.add(brand)
    });
  }


  edit(brand: Brand) {
    this.openEditDialog({
      brand,
      save: (brand) => this.brandService.update(brand)
    });
  }

  openEditDialog(data: IEditDialogData) {
    this.matDialog
      .open(EditDialogComponent, { data })
      .afterClosed()
      .subscribe((success: boolean) => success && this.loadBrands());
  }

  delete(brand: Brand) {
    this.matDialog
      .open(DeleteDialogComponent, { data: { brand } })
      .afterClosed()
      .subscribe((success: boolean) => success && this.loadBrands());
  }
}
