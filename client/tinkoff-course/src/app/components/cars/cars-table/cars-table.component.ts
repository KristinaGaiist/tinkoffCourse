import {Component, OnInit} from '@angular/core';
import {Car} from "../../../models/car";
import {MatDialog} from "@angular/material/dialog";
import {CarService} from "../../../client/car.service";
import {DeleteCarComponent} from "./dialogs/delete-car/delete-car.component";
import {EditCarComponent, IEditCarData} from './dialogs/edit-car/edit-car.component';
import {CarCustomersComponent} from "./dialogs/car-customers/car-customers.component";
import {UserStore} from "../../../stores/user-store";

@Component({
  selector: 'app-cars-table',
  templateUrl: './cars-table.component.html',
  styleUrls: ['./cars-table.component.css']
})
export class CarsTableComponent implements OnInit {
  displayedColumns: string[] = ["id", "stateNumber", "model", "brand", "viewCustomers", "edit", "delete", "add"];
  cars: Car[] = [];

  constructor(private readonly carService: CarService,
              private readonly matDialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadCars();
  }

  async loadCars() {
    this.cars = await this.carService.getAll();
  }

  addNew() {
    this.openEditDialog({
      car: new Car(),
      save: (car) => this.carService.add(car)
    });
  }

  edit(car: Car) {
    this.openEditDialog({
      car,
      save: (car) => this.carService.update(car)
    });
  }

  openEditDialog(data: IEditCarData) {
    this.matDialog
    .open(EditCarComponent, {data})
    .afterClosed()
    .subscribe(() => this.loadCars());
  }

  delete(car: Car) {
    this.matDialog
    .open(DeleteCarComponent, {data: {car}})
    .afterClosed()
    .subscribe(() => this.loadCars());
  }

  showCustomers(car: Car) {
    this.matDialog
    .open(CarCustomersComponent, {data: {car}})
    .afterClosed()
    .subscribe(() => this.loadCars());
  }

  canModify(car: Car): boolean {
    return UserStore.canModify(car.author);
  }
}
