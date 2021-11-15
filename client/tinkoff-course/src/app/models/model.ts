import {Brand} from "./brand";
import {User} from "./user";

export class Model {
  public id: number = 0;
  public model: string = "";
  public brand: Brand = new Brand();
  public author: User = new User();
}
