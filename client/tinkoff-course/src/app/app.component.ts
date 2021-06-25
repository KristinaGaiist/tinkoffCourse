import {Component} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {UserRegistrationComponent} from "./components/register/user-registration/user-registration.component";
import {UserLoginComponent} from "./components/register/user-login/user-login.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'tinkoff-course';

  constructor(private readonly matDialog: MatDialog) { }

  openRegistration() {
    this.matDialog
    .open(UserRegistrationComponent);
  }

  openLogin() {
    this.matDialog
    .open(UserLoginComponent);
  }
}
