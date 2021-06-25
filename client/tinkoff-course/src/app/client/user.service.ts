import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Config} from "../../config";
import {User} from "../models/user";
import {LoginResult} from "../models/login-result";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private readonly http: HttpClient) {
  }

  public getRole(): Promise<string> {
    return this.http.get<string>(`${Config.baseUrl}/users/role`).toPromise();
  }

  public async register(user: User): Promise<void> {
    await this.http.post(`${Config.baseUrl}/users/registration`, user).toPromise();
  }

  public login(username: string, password: string): Promise<LoginResult> {
    let body = new FormData();
    body.append('username', username);
    body.append('password', password);
    body.append('grant_type', "password");

    return this.http.post<LoginResult>(`http://testjwtclientid:XY7kmzoNzl100@localhost:6060/oauth/token`, body.toString()).toPromise();
  }
}
