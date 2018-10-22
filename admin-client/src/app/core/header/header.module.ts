import { NgModule } from '@angular/core';
import { CoreHeaderComponent } from './core-header/core-header.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
    CoreHeaderComponent,
  ],
  exports: [
    CoreHeaderComponent,
  ],
})
export class HeaderModule { }
