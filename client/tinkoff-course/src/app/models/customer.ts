import {City} from "./city";
import {User} from "./user";

export class Customer {
  public id: number = 0;
  public firstName: string = "";
  public lastName: string = "";
  public middleName: string = "";
  public city: City = new City();
  public author: User = new User();
}
