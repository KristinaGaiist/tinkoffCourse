import {City} from "./city";

export class Customer {
  public id: number = 0;
  public firstName: string = "";
  public lastName: string = "";
  public middleName: string = "";
  public city: City = new City();
}
