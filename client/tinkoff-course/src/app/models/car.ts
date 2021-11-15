import {Model} from "./model";
import {User} from "./user";

export class Car {
  public id: number = 0;
  public stateNumber: string = "";
  public model: Model = new Model();
  public author: User = new User();
}
