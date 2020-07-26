import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { OfVTestModule } from '../../../test.module';
import { ArchivoFacturasComponent } from 'app/entities/archivo-facturas/archivo-facturas.component';
import { ArchivoFacturasService } from 'app/entities/archivo-facturas/archivo-facturas.service';
import { ArchivoFacturas } from 'app/shared/model/archivo-facturas.model';

describe('Component Tests', () => {
  describe('ArchivoFacturas Management Component', () => {
    let comp: ArchivoFacturasComponent;
    let fixture: ComponentFixture<ArchivoFacturasComponent>;
    let service: ArchivoFacturasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfVTestModule],
        declarations: [ArchivoFacturasComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(ArchivoFacturasComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArchivoFacturasComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArchivoFacturasService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ArchivoFacturas(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.archivoFacturas && comp.archivoFacturas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ArchivoFacturas(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.archivoFacturas && comp.archivoFacturas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
