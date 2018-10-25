import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CoreBodyComponent } from './core-body/core-body.component';
import { DetailViewComponent } from './detail-view/detail-view.component';
import { EditViewComponent } from './edit-view/edit-view.component';

const routes: Routes = [
  { path: 'list', component: CoreBodyComponent },
  { path: 'detail', component: DetailViewComponent },
  { path: 'edit', component: EditViewComponent }
];

@NgModule({
  imports: [
    RouterModule,
    RouterModule.forChild(routes)
  ],
  declarations: []
})
export class BodyRoutingModule { }
