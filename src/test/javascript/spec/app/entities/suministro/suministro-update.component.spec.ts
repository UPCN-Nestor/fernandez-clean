import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OfVTestModule } from '../../../test.module';
import { SuministroUpdateComponent } from 'app/entities/suministro/suministro-update.component';
import { SuministroService } from 'app/entities/suministro/suministro.service';
import { Suministro } from 'app/shared/model/suministro.model';

describe('Component Tests', () => {
  describe('Suministro Management Update Component', () => {
    let comp: SuministroUpdateComponent;
    let fixture: ComponentFixture<SuministroUpdateComponent>;
    let service: SuministroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfVTestModule],
        declarations: [SuministroUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SuministroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SuministroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SuministroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Suministro(123);
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
        const entity = new Suministro();
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
