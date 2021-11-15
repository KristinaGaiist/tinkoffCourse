import {Component} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {UserService} from "../../../client/user.service";
import {LoginResult} from "../../../models/login-result";
import {UserStore} from "../../../stores/user-store";
import {CurrentUser} from "../../../models/current-user";

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent {
  username: string = "";
  password: string = "";

  constructor(public dialogRef: MatDialogRef<UserLoginComponent>,
              private readonly userService: UserService) {
  }

  async login() {
    const result: LoginResult = await this.userService.login(this.username, this.password);
    UserStore.userAuthorized(result.access_token);

    const currentUser: CurrentUser = await this.userService.getCurrentUser();
    UserStore.initCurrentUser(currentUser);

    this.dialogRef.close();
  }

  cancel() {
    this.dialogRef.close();
  }
}
