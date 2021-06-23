import {Component, OnInit} from '@angular/core';
import {City} from "../../../models/city";
import {MatDialog} from "@angular/material/dialog";
import {CityService} from "../../../client/city.service";
import {EditCityComponent, IEditCityData} from "./dialogs/edit-city/edit-city.component";
import {DeleteCityComponent} from "./dialogs/delete-city/delete-city.component";

@Component({
  selector: 'app-cities-table',
  templateUrl: './cities-table.component.html',
  styleUrls: ['./cities-table.component.css']
})
export class CitiesTableComponent implements OnInit {
  displayedColumns: string[] = ["id", "name", "edit", "delete", "add"];
  cities: City[] = [];

  constructor(private readonly cityService: CityService,
              private readonly matDialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadCities();
  }

  async loadCities() {
    this.cities = await this.cityService.getAll();
  }

  addNew() {
    this.openEditDialog({
      city: new City(),
      save: (city) => this.cityService.add(city)
    });
  }

  edit(city: City) {
    this.openEditDialog({
      city,
      save: (city) => this.cityService.update(city)
    });
  }

  openEditDialog(data: IEditCityData) {
    this.matDialog
    .open(EditCityComponent, {data})
    .afterClosed()
    .subscribe(() => this.loadCities());
  }

  delete(city: City) {
    this.matDialog
    .open(DeleteCityComponent, {data: {city}})
    .afterClosed()
    .subscribe(() => this.loadCities());
  }
}
