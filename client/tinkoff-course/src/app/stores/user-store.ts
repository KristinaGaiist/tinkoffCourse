import {CurrentUser, CurrentUserRecord} from "../models/current-user";
import {Role} from "../models/role";
import {User} from "../models/user";

export class UserStore {
  private static readonly TOKEN_KEY = "access_token";
  private static readonly USER_KEY = "current_user";

  public static userAuthorized(token: string) {
    sessionStorage.setItem(this.TOKEN_KEY, token);
  }

  public static initCurrentUser(currentUser: CurrentUser) {
    const userRecord = currentUser.toRecord();
    sessionStorage.setItem(this.USER_KEY, JSON.stringify(userRecord));
  }

  public static userUnauthorized() {
    sessionStorage.removeItem(this.TOKEN_KEY);
    sessionStorage.removeItem(this.USER_KEY);
  }

  public static getToken() {
    return sessionStorage.getItem(this.TOKEN_KEY);
  }

  public static get isAuthorized(): boolean {
    return sessionStorage.getItem(this.TOKEN_KEY) != null;
  }

  public static getCurrentUser(): CurrentUser | null {
    let item = sessionStorage.getItem(this.USER_KEY);
    if (item == null) {
      return null;
    }

    const userRecord: CurrentUserRecord = JSON.parse(item);
    return new CurrentUser(userRecord);
  }

  public static canModify(author: User): boolean {
    const currentUser = UserStore.getCurrentUser();
    if (currentUser == null) {
      return false;
    }

    return currentUser.role === Role.ADMIN || currentUser.username === author.username;
  }
}
