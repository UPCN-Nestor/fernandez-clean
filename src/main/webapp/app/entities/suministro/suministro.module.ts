import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OfVSharedModule } from 'app/shared/shared.module';
import { SuministroComponent } from './suministro.component';
import { SuministroDetailComponent } from './suministro-detail.component';
import { SuministroUpdateComponent } from './suministro-update.component';
import { SuministroDeleteDialogComponent } from './suministro-delete-dialog.component';
import { suministroRoute } from './suministro.route';

@NgModule({
  imports: [OfVSharedModule, RouterModule.forChild(suministroRoute)],
  declarations: [SuministroComponent, SuministroDetailComponent, SuministroUpdateComponent, SuministroDeleteDialogComponent],
  entryComponents: [SuministroDeleteDialogComponent]
})
export class OfVSuministroModule {}
