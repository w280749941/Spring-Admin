import { NgModule } from '@angular/core';
import { CoreBodyComponent } from './core-body/core-body.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { BodyRoutingModule } from './body-routing.module';
import { DetailViewComponent } from './detail-view/detail-view.component';
import { Data } from './Data';
import { EditViewComponent } from './edit-view/edit-view.component';

@NgModule({
  imports: [
    SharedModule,
    BodyRoutingModule,
  ],
  declarations: [
    CoreBodyComponent,
    DetailViewComponent,
    EditViewComponent,
  ],
  exports: [
    CoreBodyComponent,
    DetailViewComponent,
    BodyRoutingModule,
  ],
  providers: [Data]
})
export class BodyModule { }
