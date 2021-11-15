import {Role} from "./role";

export class CurrentUserRecord {
  public username: string | null | undefined;
  public lastName: string | null | undefined;
  public firstName: string | null | undefined;
  public role: string | null | undefined;
}

export class CurrentUser {

  private _username: string;
  private _lastName: string;
  private _firstName: string;
  private _role: Role = Role.USER;

  constructor(record: CurrentUserRecord) {
    if (record.username == null ||
        record.firstName == null || record.lastName == null ||
        (record.role != "USER" && record.role != "ADMIN")) {
      throw new Error(`Invalid record - ${JSON.stringify(record)}`);
    }

    this._username = record.username;
    this._lastName = record.lastName;
    this._firstName = record.firstName;
    this._role = Role[record.role];
  }

  public get username() {
    return this._username;
  }

  public get firstName() {
    return this._firstName;
  }

  public get lastName() {
    return this._lastName;
  }

  public get role() {
    return this._role;
  }

  public toRecord(): CurrentUserRecord {
    const record = new CurrentUserRecord();
    record.username = this._username;
    record.firstName = this._firstName;
    record.role = Role[this.role];
    record.lastName = this._lastName;

    return record;
  }
}
