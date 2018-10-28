import { NgModule } from '@angular/core';
import { AppRoutingModule } from '../app-routing.module';
import { BodyModule } from './body/body.module';
import { HeaderModule } from './header/header.module';
import { FooterModule } from './footer/footer.module';
import { ProjectModule } from '../project/project.module';

@NgModule({
  imports: [
    AppRoutingModule,
    HeaderModule,
    BodyModule,
    FooterModule,
  ],
  declarations: [
  ],
  exports: [
    AppRoutingModule,
    HeaderModule,
    BodyModule,
    FooterModule,
  ],
})
export class CoreModule { }
