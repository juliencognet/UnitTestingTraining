import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { FicTestsAutomatisesSharedModule } from 'app/shared/shared.module';
import { FicTestsAutomatisesCoreModule } from 'app/core/core.module';
import { FicTestsAutomatisesAppRoutingModule } from './app-routing.module';
import { FicTestsAutomatisesHomeModule } from './home/home.module';
import { FicTestsAutomatisesEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    FicTestsAutomatisesSharedModule,
    FicTestsAutomatisesCoreModule,
    FicTestsAutomatisesHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    FicTestsAutomatisesEntityModule,
    FicTestsAutomatisesAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class FicTestsAutomatisesAppModule {}
