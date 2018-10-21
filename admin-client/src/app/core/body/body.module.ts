import { NgModule } from '@angular/core';
import { CoreBodyComponent } from './core-body/core-body.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [
    SharedModule,
  ],
  declarations: [
    CoreBodyComponent,
  ],
  exports: [
    CoreBodyComponent,
  ]
})
export class BodyModule { }
