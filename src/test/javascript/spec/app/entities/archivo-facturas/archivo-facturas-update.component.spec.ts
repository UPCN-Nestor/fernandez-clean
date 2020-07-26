import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OfVTestModule } from '../../../test.module';
import { ArchivoFacturasUpdateComponent } from 'app/entities/archivo-facturas/archivo-facturas-update.component';
import { ArchivoFacturasService } from 'app/entities/archivo-facturas/archivo-facturas.service';
import { ArchivoFacturas } from 'app/shared/model/archivo-facturas.model';

describe('Component Tests', () => {
  describe('ArchivoFacturas Management Update Component', () => {
    let comp: ArchivoFacturasUpdateComponent;
    let fixture: ComponentFixture<ArchivoFacturasUpdateComponent>;
    let service: ArchivoFacturasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfVTestModule],
        declarations: [ArchivoFacturasUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ArchivoFacturasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArchivoFacturasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArchivoFacturasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ArchivoFacturas(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ArchivoFacturas();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
