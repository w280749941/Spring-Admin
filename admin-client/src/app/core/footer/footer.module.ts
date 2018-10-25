import { NgModule } from '@angular/core';
import { CoreFooterComponent } from './core-footer/core-footer.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [
    SharedModule,
  ],
  declarations: [
    CoreFooterComponent,
  ],
  exports: [
    CoreFooterComponent,
  ],
})
export class FooterModule { }
