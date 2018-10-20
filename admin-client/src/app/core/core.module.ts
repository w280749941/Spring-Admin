import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from '../app-routing.module';
import { CoreBodyComponent } from './body/core-body/core-body.component';
import { CoreFooterComponent } from './footer/core-footer/core-footer.component';
import { CoreHeaderComponent } from './header/core-header/core-header.component';

@NgModule({
  imports: [
    CommonModule,
    AppRoutingModule,
  ],
  declarations: [
    CoreBodyComponent,
    CoreFooterComponent,
    CoreHeaderComponent,
  ],
  exports: [
    AppRoutingModule,
    CoreBodyComponent,
    CoreFooterComponent,
    CoreHeaderComponent,
  ]
})
export class CoreModule { }
