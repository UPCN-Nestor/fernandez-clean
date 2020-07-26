import { IUser } from 'app/core/user/user.model';

export interface ISuministro {
  id?: number;
  suministro?: string;
  servicio?: string;
  inmueble?: string;
  usuario?: string;
  dni?: string;
  tarifa?: string;
  users?: IUser[];
}

export class Suministro implements ISuministro {
  constructor(
    public id?: number,
    public suministro?: string,
    public servicio?: string,
    public inmueble?: string,
    public usuario?: string,
    public dni?: string,
    public tarifa?: string,
    public users?: IUser[]
  ) {}
}
