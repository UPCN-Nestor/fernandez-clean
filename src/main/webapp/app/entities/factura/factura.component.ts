import { Component, OnInit, OnDestroy } from '@angular/core';

import { FacturaService } from './factura.service';

@Component({
  selector: 'jhi-factura',
  templateUrl: './factura.component.html'
})
export class FacturaComponent implements OnInit, OnDestroy {
  constructor(protected facturaService: FacturaService) {}

  ngOnInit(): void {}

  ngOnDestroy(): void {}
}
