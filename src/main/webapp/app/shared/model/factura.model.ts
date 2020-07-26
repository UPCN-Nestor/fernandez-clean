import { Moment } from 'moment';
import { IArchivoFacturas } from 'app/shared/model/archivo-facturas.model';

export interface IFactura {
  id?: number;
  suministro?: string;
  usuario?: string;
  inmueble?: string;
  periodo?: string;
  numero?: string;
  vencimiento1?: Moment;
  vencimiento2?: Moment;
  importe1?: number;
  importe2?: number;
  servicio?: string;
  tarifa?: string;
  archivopdf?: string;
  estado?: string;
  dni?: string;
  socio?: string;
  archivoFacturas?: IArchivoFacturas;
}

export class Factura implements IFactura {
  constructor(
    public id?: number,
    public suministro?: string,
    public usuario?: string,
    public inmueble?: string,
    public periodo?: string,
    public numero?: string,
    public vencimiento1?: Moment,
    public vencimiento2?: Moment,
    public importe1?: number,
    public importe2?: number,
    public servicio?: string,
    public tarifa?: string,
    public archivopdf?: string,
    public estado?: string,
    public dni?: string,
    public socio?: string,
    public archivoFacturas?: IArchivoFacturas
  ) {}
}
