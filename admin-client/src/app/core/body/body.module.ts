import { NgModule } from '@angular/core';
import { CoreBodyComponent } from './core-body/core-body.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { BodyRoutingModule } from './body-routing.module';
import { Data } from './Data';

@NgModule({
  imports: [
    SharedModule,
    BodyRoutingModule,
  ],
  declarations: [
    CoreBodyComponent,
  ],
  exports: [
    CoreBodyComponent,
    BodyRoutingModule,
  ],
  providers: [Data]
})
export class BodyModule { }
