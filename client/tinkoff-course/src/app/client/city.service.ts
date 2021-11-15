import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Config} from "../../config";
import {City} from "../models/city";

@Injectable({
  providedIn: 'root'
})
export class CityService {

  constructor(private readonly http: HttpClient) {
  }

  public getAll(): Promise<City[]> {
    return this.http.get<City[]>(`${Config.baseUrl}/cities`).toPromise();
  }

  public async add(city: City): Promise<void> {
    await this.http.post(`${Config.baseUrl}/cities`, city).toPromise();
  }

  public async update(city: City): Promise<void> {
    await this.http.put(`${Config.baseUrl}/cities`, city).toPromise();
  }

  public async delete(city: City): Promise<void> {
    await this.http.delete(`${Config.baseUrl}/cities/${city.id}`).toPromise();
  }
}
