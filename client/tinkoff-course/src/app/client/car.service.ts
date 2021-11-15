import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Config} from "../../config";
import {Car} from "../models/car";
import {Customer} from "../models/customer";

@Injectable({
  providedIn: 'root'
})
export class CarService {

  constructor(private readonly http: HttpClient) {
  }

  public getAll(): Promise<Car[]> {
    return this.http.get<Car[]>(`${Config.baseUrl}/cars`).toPromise();
  }

  public async add(car: Car): Promise<void> {
    await this.http.post(`${Config.baseUrl}/cars`, car).toPromise();
  }

  public async update(car: Car): Promise<void> {
    await this.http.put(`${Config.baseUrl}/cars`, car).toPromise();
  }

  public async delete(car: Car): Promise<void> {
    await this.http.delete(`${Config.baseUrl}/cars/${car.id}`).toPromise();
  }

  public getCustomers(car: Car): Promise<Customer[]> {
    return this.http.get<Customer[]>(`${Config.baseUrl}/cars/${car.id}/customers`).toPromise();
  }

  public async addCustomer(car: Car, customer: Customer): Promise<void> {
    await this.http.post(`${Config.baseUrl}/cars/${car.id}/customers/${customer.id}`, {}).toPromise();
  }

  public async deleteCustomer(car: Car, customer: Customer): Promise<void> {
    await this.http.delete(`${Config.baseUrl}/cars/${car.id}/customers/${customer.id}`).toPromise();
  }
}
