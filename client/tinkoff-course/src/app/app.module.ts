import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {NgModule} from '@angular/core';
import {FlexLayoutModule} from '@angular/flex-layout';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from '@angular/material/input';
import {MatListModule} from "@angular/material/list";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatTableModule} from "@angular/material/table";
import {MatToolbarModule} from "@angular/material/toolbar";
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrandsTableComponent} from './components/brands/brands-table/brands-table/brands-table.component';
import {DeleteDialogComponent} from './components/brands/brands-table/brands-table/dialogs/delete/delete.dialog.component';
import {EditDialogComponent} from './components/brands/brands-table/brands-table/dialogs/edit/edit.dialog.component';
import {ModelsTableComponent} from './components/models/models-table/models-table/models-table.component';
import {DeleteModelComponent} from './components/models/models-table/models-table/dialogs/delete-model/delete-model.component';
import {EditModelComponent} from './components/models/models-table/models-table/dialogs/edit-model/edit-model.component';
import {MatSelectModule} from "@angular/material/select";
import {CitiesTableComponent} from './components/cities/cities-table/cities-table.component';
import {DeleteCityComponent} from './components/cities/cities-table/dialogs/delete-city/delete-city.component';
import {EditCityComponent} from './components/cities/cities-table/dialogs/edit-city/edit-city.component';
import {CarsTableComponent} from './components/cars/cars-table/cars-table.component';
import {CustomersTableComponent} from './components/customers/customers-table/customers-table.component';
import {DeleteCarComponent} from './components/cars/cars-table/dialogs/delete-car/delete-car.component';
import {EditCarComponent} from './components/cars/cars-table/dialogs/edit-car/edit-car.component';
import {DeleteCustomerComponent} from './components/customers/customers-table/dialogs/delete-customer/delete-customer.component';
import {EditCustomerComponent} from './components/customers/customers-table/dialogs/edit-customer/edit-customer.component';
import {CarCustomersComponent} from './components/cars/cars-table/dialogs/car-customers/car-customers.component';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {CustomerCarsComponent} from './components/customers/customers-table/dialogs/customer-cars/customer-cars.component';
import {UserRegistrationComponent} from './components/register/user-registration/user-registration.component';
import {UserLoginComponent} from './components/register/user-login/user-login.component';
import {AuthInterceptor} from "./client/interceptors/auth.interceptor";
import {ErrorInterceptor} from "./client/interceptors/error.interceptor";
import {MatSnackBarModule} from "@angular/material/snack-bar";

@NgModule({
  declarations: [
    AppComponent,
    BrandsTableComponent,
    EditDialogComponent,
    DeleteDialogComponent,
    ModelsTableComponent,
    DeleteModelComponent,
    EditModelComponent,
    CitiesTableComponent,
    DeleteCityComponent,
    EditCityComponent,
    CarsTableComponent,
    CustomersTableComponent,
    DeleteCarComponent,
    EditCarComponent,
    DeleteCustomerComponent,
    EditCustomerComponent,
    CarCustomersComponent,
    CustomerCarsComponent,
    UserRegistrationComponent,
    UserLoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatDialogModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    HttpClientModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    MatInputModule,
    FormsModule,
    MatCardModule,
    MatButtonModule,
    MatSelectModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    MatSnackBarModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  exports: []
})
export class AppModule {
}
