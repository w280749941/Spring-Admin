import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';

@NgModule({
  imports: [
    SharedModule,
  ],
  declarations: [
    ConfirmDialogComponent,
  ],
  exports: [
    ConfirmDialogComponent,
  ],
  entryComponents: [
    ConfirmDialogComponent,
  ],
})
export class ProjectModule { }
