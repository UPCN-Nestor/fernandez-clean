import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ArchivoFacturasService } from 'app/entities/archivo-facturas/archivo-facturas.service';
import { IArchivoFacturas, ArchivoFacturas } from 'app/shared/model/archivo-facturas.model';

describe('Service Tests', () => {
  describe('ArchivoFacturas Service', () => {
    let injector: TestBed;
    let service: ArchivoFacturasService;
    let httpMock: HttpTestingController;
    let elemDefault: IArchivoFacturas;
    let expectedResult: IArchivoFacturas | IArchivoFacturas[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ArchivoFacturasService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ArchivoFacturas(0, 'AAAAAAA', currentDate, 'image/png', 'AAAAAAA', 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ArchivoFacturas', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fecha: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate
          },
          returnedFromService
        );

        service.create(new ArchivoFacturas()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ArchivoFacturas', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            fecha: currentDate.format(DATE_FORMAT),
            archivoBlob: 'BBBBBB',
            archivoCsv: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ArchivoFacturas', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            fecha: currentDate.format(DATE_FORMAT),
            archivoBlob: 'BBBBBB',
            archivoCsv: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ArchivoFacturas', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
