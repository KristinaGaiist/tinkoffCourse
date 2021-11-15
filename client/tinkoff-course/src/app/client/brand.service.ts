import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Brand} from "../models/brand";
import {Config} from "../../config";

@Injectable({
  providedIn: 'root'
})
export class BrandService {

  constructor(private readonly http: HttpClient) {
  }

  public getAll(): Promise<Brand[]> {
    return this.http.get<Brand[]>(`${Config.baseUrl}/brands`).toPromise();
  }

  public async add(brand: Brand): Promise<void> {
    await this.http.post(`${Config.baseUrl}/brands`, brand).toPromise();
  }

  public async update(brand: Brand): Promise<void> {
    await this.http.put(`${Config.baseUrl}/brands`, brand).toPromise();
  }

  public async delete(brand: Brand): Promise<void> {
    await this.http.delete(`${Config.baseUrl}/brands/${brand.id}`).toPromise();
  }
}
