import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OfVTestModule } from '../../../test.module';
import { SuministroDetailComponent } from 'app/entities/suministro/suministro-detail.component';
import { Suministro } from 'app/shared/model/suministro.model';

describe('Component Tests', () => {
  describe('Suministro Management Detail Component', () => {
    let comp: SuministroDetailComponent;
    let fixture: ComponentFixture<SuministroDetailComponent>;
    const route = ({ data: of({ suministro: new Suministro(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OfVTestModule],
        declarations: [SuministroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SuministroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SuministroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load suministro on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.suministro).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
