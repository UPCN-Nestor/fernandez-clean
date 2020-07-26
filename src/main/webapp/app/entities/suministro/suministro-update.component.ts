import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISuministro, Suministro } from 'app/shared/model/suministro.model';
import { SuministroService } from './suministro.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-suministro-update',
  templateUrl: './suministro-update.component.html'
})
export class SuministroUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    suministro: [],
    servicio: [],
    inmueble: [],
    usuario: [],
    dni: [],
    tarifa: [],
    users: []
  });

  constructor(
    protected suministroService: SuministroService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ suministro }) => {
      this.updateForm(suministro);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(suministro: ISuministro): void {
    this.editForm.patchValue({
      id: suministro.id,
      suministro: suministro.suministro,
      servicio: suministro.servicio,
      inmueble: suministro.inmueble,
      usuario: suministro.usuario,
      dni: suministro.dni,
      tarifa: suministro.tarifa,
      users: suministro.users
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const suministro = this.createFromForm();
    if (suministro.id !== undefined) {
      this.subscribeToSaveResponse(this.suministroService.update(suministro));
    } else {
      this.subscribeToSaveResponse(this.suministroService.create(suministro));
    }
  }

  private createFromForm(): ISuministro {
    return {
      ...new Suministro(),
      id: this.editForm.get(['id'])!.value,
      suministro: this.editForm.get(['suministro'])!.value,
      servicio: this.editForm.get(['servicio'])!.value,
      inmueble: this.editForm.get(['inmueble'])!.value,
      usuario: this.editForm.get(['usuario'])!.value,
      dni: this.editForm.get(['dni'])!.value,
      tarifa: this.editForm.get(['tarifa'])!.value,
      users: this.editForm.get(['users'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISuministro>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }

  getSelected(selectedVals: IUser[], option: IUser): IUser {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
