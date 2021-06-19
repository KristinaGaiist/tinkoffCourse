import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BrandsTableComponent} from "./components/brands/brands-table/brands-table/brands-table.component";
import {AppComponent} from "./app.component";

const routes: Routes = [
  { path: "brands", component: BrandsTableComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
