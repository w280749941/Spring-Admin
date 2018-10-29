import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CoreBodyComponent } from './core-body/core-body.component';

const routes: Routes = [
  { path: 'list', component: CoreBodyComponent },
];

@NgModule({
  imports: [
    RouterModule,
    RouterModule.forChild(routes)
  ],
  declarations: []
})
export class BodyRoutingModule { }
