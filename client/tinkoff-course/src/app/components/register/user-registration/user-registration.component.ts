import {Component} from '@angular/core';
import {RegisterUserRequest} from "../../../models/register-user-request";
import {UserService} from "../../../client/user.service";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent {
  user: RegisterUserRequest = new RegisterUserRequest();

  constructor(public dialogRef: MatDialogRef<UserRegistrationComponent>,
              private readonly userService: UserService) {
  }

  async register() {
    await this.userService.register(this.user);
    this.dialogRef.close();
  }

  cancel() {
    this.dialogRef.close();
  }
}
