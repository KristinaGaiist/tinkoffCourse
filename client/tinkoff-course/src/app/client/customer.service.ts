import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Config} from "../../config";
import {Customer} from "../models/customer";
import {Car} from "../models/car";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private readonly http: HttpClient) {
  }

  public getAll(): Promise<Customer[]> {
    return this.http.get<Customer[]>(`${Config.baseUrl}/customers`).toPromise();
  }

  public async add(customer: Customer): Promise<void> {
    await this.http.post(`${Config.baseUrl}/customers`, customer).toPromise();
  }

  public async update(customer: Customer): Promise<void> {
    await this.http.put(`${Config.baseUrl}/customers`, customer).toPromise();
  }

  public async delete(customer: Customer): Promise<void> {
    await this.http.delete(`${Config.baseUrl}/customers/${customer.id}`).toPromise();
  }

  public getCars(customer: Customer): Promise<Car[]> {
    return this.http.get<Car[]>(`${Config.baseUrl}/customers/${customer.id}/cars`).toPromise();
  }

  public async addCar(customer: Customer, car: Car): Promise<void> {
    await this.http.post(`${Config.baseUrl}/customers/${customer.id}/cars/${car.id}`, customer).toPromise();
  }

  public async deleteCar(customer: Customer, car: Car): Promise<void> {
    await this.http.delete(`${Config.baseUrl}/customers/${customer.id}/cars/${car.id}`).toPromise();
  }
}
