import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FacturaService } from 'app/entities/factura/factura.service';
import { IFactura, Factura } from 'app/shared/model/factura.model';

describe('Service Tests', () => {
  describe('Factura Service', () => {
    let injector: TestBed;
    let service: FacturaService;
    let httpMock: HttpTestingController;
    let elemDefault: IFactura;
    let expectedResult: IFactura | IFactura[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FacturaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Factura(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            vencimiento1: currentDate.format(DATE_FORMAT),
            vencimiento2: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Factura', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            vencimiento1: currentDate.format(DATE_FORMAT),
            vencimiento2: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vencimiento1: currentDate,
            vencimiento2: currentDate
          },
          returnedFromService
        );

        service.create(new Factura()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Factura', () => {
        const returnedFromService = Object.assign(
          {
            suministro: 'BBBBBB',
            usuario: 'BBBBBB',
            inmueble: 'BBBBBB',
            periodo: 'BBBBBB',
            numero: 'BBBBBB',
            vencimiento1: currentDate.format(DATE_FORMAT),
            vencimiento2: currentDate.format(DATE_FORMAT),
            importe1: 1,
            importe2: 1,
            servicio: 'BBBBBB',
            tarifa: 'BBBBBB',
            archivopdf: 'BBBBBB',
            estado: 'BBBBBB',
            dni: 'BBBBBB',
            socio: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vencimiento1: currentDate,
            vencimiento2: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Factura', () => {
        const returnedFromService = Object.assign(
          {
            suministro: 'BBBBBB',
            usuario: 'BBBBBB',
            inmueble: 'BBBBBB',
            periodo: 'BBBBBB',
            numero: 'BBBBBB',
            vencimiento1: currentDate.format(DATE_FORMAT),
            vencimiento2: currentDate.format(DATE_FORMAT),
            importe1: 1,
            importe2: 1,
            servicio: 'BBBBBB',
            tarifa: 'BBBBBB',
            archivopdf: 'BBBBBB',
            estado: 'BBBBBB',
            dni: 'BBBBBB',
            socio: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            vencimiento1: currentDate,
            vencimiento2: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Factura', () => {
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
