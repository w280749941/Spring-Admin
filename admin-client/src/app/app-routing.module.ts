import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CoreBodyComponent } from './core/body/core-body/core-body.component';

const routes: Routes = [
  { path: '', component: CoreBodyComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
