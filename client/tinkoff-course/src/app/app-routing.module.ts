import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BrandsTableComponent} from "./components/brands/brands-table/brands-table/brands-table.component";
import {ModelsTableComponent} from "./components/models/models-table/models-table/models-table.component";
import {CitiesTableComponent} from "./components/cities/cities-table/cities-table.component";
import {CustomersTableComponent} from "./components/customers/customers-table/customers-table.component";
import {CarsTableComponent} from "./components/cars/cars-table/cars-table.component";

const routes: Routes = [
  {path: "brands", component: BrandsTableComponent},
  {path: "models", component: ModelsTableComponent},
  {path: "cities", component: CitiesTableComponent},
  {path: "customers", component: CustomersTableComponent},
  {path: "cars", component: CarsTableComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
