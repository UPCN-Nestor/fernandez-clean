import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { OfVSharedModule } from 'app/shared/shared.module';
import { OfVCoreModule } from 'app/core/core.module';
import { OfVAppRoutingModule } from './app-routing.module';
import { OfVHomeModule } from './home/home.module';
import { OfVEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    OfVSharedModule,
    OfVCoreModule,
    OfVHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    OfVEntityModule,
    OfVAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class OfVAppModule {}
