import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Model} from "../models/model";
import {Config} from "../../config";

@Injectable({
  providedIn: 'root'
})
export class ModelService {

  constructor(private readonly http: HttpClient) { }

  public getAll(): Promise<Model[]> {
    return this.http.get<Model[]>(`${Config.baseUrl}/models`).toPromise();
  }

  public async add(model: Model): Promise<void> {
    await this.http.post(`${Config.baseUrl}/models`, model).toPromise();
  }

  public async update(model: Model): Promise<void> {
    await this.http.put(`${Config.baseUrl}/models`, model).toPromise();
  }

  public async delete(model: Model): Promise<void> {
    await this.http.delete(`${Config.baseUrl}/models/${model.id}`).toPromise();
  }
}
