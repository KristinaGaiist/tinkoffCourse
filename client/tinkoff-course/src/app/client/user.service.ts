import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Config} from "../../config";
import {RegisterUserRequest} from "../models/register-user-request";
import {LoginResult} from "../models/login-result";
import {CurrentUser, CurrentUserRecord} from "../models/current-user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private readonly http: HttpClient) {
  }

  public async getCurrentUser(): Promise<CurrentUser> {
    const currentUserRecord = await this.http.get<CurrentUserRecord>(`${Config.baseUrl}/users`).toPromise();
    return new CurrentUser(currentUserRecord);
  }

  public async register(user: RegisterUserRequest): Promise<void> {
    await this.http.post(`${Config.baseUrl}/users/registration`, user).toPromise();
  }

  public async login(username: string, password: string): Promise<LoginResult> {

    const headers = new HttpHeaders(username && password ? {
      authorization: 'Basic ' + btoa(username + ':' + password)
    } : {});

    await this.http.get(`${Config.baseUrl}/basicAuth`, {headers: headers}).toPromise();

    const result = new LoginResult();
    result.access_token = btoa(username + ':' + password);

    return result;
  }

  public async logout() {
    await this.http.post(`${Config.baseUrl}/basicAuth`, {}).toPromise();
  }
}
