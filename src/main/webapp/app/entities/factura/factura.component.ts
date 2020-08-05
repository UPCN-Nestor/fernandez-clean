import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from '@angular/core';
import { HttpResponse, HttpClient } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFactura } from 'app/shared/model/factura.model';
import { FacturaService } from './factura.service';
import { FacturaDeleteDialogComponent } from './factura-delete-dialog.component';
import { Suministro, ISuministro } from 'app/shared/model/suministro.model';
import { SuministroService } from '../suministro/suministro.service';
import { saveAs } from 'file-saver';

@Component({
  selector: 'jhi-factura',
  templateUrl: './factura.component.html'
})
export class FacturaComponent implements OnInit, OnDestroy {
  facturas?: IFactura[];
  eventSubscriber?: Subscription;

  activo: Suministro;
  suministros?: ISuministro[];

  nuevoSuministro: string;
  nuevoDni: string;

  confirmarVisible: boolean;

  @ViewChild('bottom') bottomRef: ElementRef;

  constructor(
    protected facturaService: FacturaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected suministroService: SuministroService,
    protected http: HttpClient
  ) {}

  loadAll(): void {
    /*
    if(this.activo)
      this.facturaService.allBySumi(this.activo.id).subscribe((res: HttpResponse<IFactura[]>) => (this.facturas = res.body || []));*/
  }

  ngOnInit(): void {
    this.suministroService.allByLoggedUser().subscribe((res: HttpResponse<ISuministro[]>) => {
      this.suministros = res.body || [];
      if (this.suministros.length > 0) {
        this.cambiarSuministro(this.suministros[0]);
      }
    });

    this.loadAll();
    this.registerChangeInFacturas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFactura): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFacturas(): void {
    this.eventSubscriber = this.eventManager.subscribe('facturaListModification', () => this.loadAll());
  }

  delete(factura: IFactura): void {
    const modalRef = this.modalService.open(FacturaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.factura = factura;
  }

  agregarSuministro() {
    this.suministroService.createConNumeroYDNI(this.nuevoSuministro, this.nuevoDni).subscribe((res: HttpResponse<ISuministro>) => {
      this.suministros.push(res.body);
      this.activo = res.body;

      this.bottomRef.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
    });
  }

  confirmarDesvincular() {
    this.confirmarVisible = true;
  }

  removerSuministro() {
    this.confirmarVisible = false;
    this.suministros.splice(this.suministros.indexOf(this.activo), 1);
    this.suministroService.delete(this.activo.id).subscribe((res: HttpResponse<ISuministro>) => {});
  }

  cambiarSuministro(suministro: ISuministro) {
    this.activo = suministro;
    this.loadAllBySumi(suministro.suministro);

    this.bottomRef.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }

  loadAllBySumi(sumi: string): void {
    this.facturaService.allBySumi(sumi).subscribe((res: HttpResponse<IFactura[]>) => {
      this.facturas = res.body || [];
    });
  }

  /*
  verComprobante(factura: IFactura) {
    
    this.facturaService.verComprobante(factura).subscribe(data => {
      // let httpData=data;
      const file = new Blob([data], { type: 'application/pdf' });
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL);
    
    });
  }*/

  verComprobante(factura: IFactura) {
    const myUrl = '/api/facturas/pdf'; // + factura.id;
    const mediaType = 'application/pdf';
    const copy = this.facturaService.convertDateFromClient(factura);
    this.http.post(myUrl, copy, { responseType: 'blob' }).subscribe(
      response => {
        const blob = new Blob([response], { type: mediaType });
        saveAs(blob, 'Comprobante-' + factura.numero + '--' + factura.periodo + '.pdf');
      },
      e => {
        alert('error');
      }
    );
  }
}
