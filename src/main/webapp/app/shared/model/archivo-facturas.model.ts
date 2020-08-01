import { Moment } from 'moment';

export interface IArchivoFacturas {
  id?: number;
  nombre?: string;
  fecha?: Moment;
  archivoBlobContentType?: string;
  archivoBlob?: any;
  archivoCsvContentType?: string;
  archivoCsv?: any;
}

export class ArchivoFacturas implements IArchivoFacturas {
  constructor(
    public id?: number,
    public nombre?: string,
    public fecha?: Moment,
    public archivoBlobContentType?: string,
    public archivoBlob?: any,
    public archivoCsvContentType?: string,
    public archivoCsv?: any
  ) {}
}
