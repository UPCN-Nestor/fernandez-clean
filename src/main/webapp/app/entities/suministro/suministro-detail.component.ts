import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISuministro } from 'app/shared/model/suministro.model';

@Component({
  selector: 'jhi-suministro-detail',
  templateUrl: './suministro-detail.component.html'
})
export class SuministroDetailComponent implements OnInit {
  suministro: ISuministro | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ suministro }) => (this.suministro = suministro));
  }

  previousState(): void {
    window.history.back();
  }
}
