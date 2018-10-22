import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CoreBodyComponent } from './core-body/core-body.component';
import { DetailViewComponent } from './detail-view/detail-view.component';

const routes: Routes = [
  { path: 'list', component: CoreBodyComponent },
  { path: 'detail', component: DetailViewComponent }
];

@NgModule({
  imports: [
    RouterModule,
    RouterModule.forChild(routes)
  ],
  declarations: []
})
export class BodyRoutingModule { }
