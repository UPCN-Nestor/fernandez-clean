import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { OfVTestModule } from '../../../test.module';
import { ArchivoFacturasDetailComponent } from 'app/entities/archivo-facturas/archivo-facturas-detail.component';
import { ArchivoFacturas } from 'app/shared/model/archivo-facturas.model';

describe('Component Tests', () => {
  describe('ArchivoFacturas Management Detail Component', () => {
    let comp: ArchivoFacturasDetailComponent;
    let fixture: ComponentFixture<ArchivoFacturasDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ archivoFacturas: new ArchivoFacturas(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfVTestModule],
        declarations: [ArchivoFacturasDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ArchivoFacturasDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArchivoFacturasDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load archivoFacturas on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.archivoFacturas).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
