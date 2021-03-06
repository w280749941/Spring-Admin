import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatTableModule, MatPaginatorModule, MatButtonModule, MatSelectModule } from '@angular/material';
import { HttpClientModule } from '@angular/common/http';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatDialogModule} from '@angular/material/dialog';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import { ModifyDialogComponent } from './modify-dialog/modify-dialog.component';
import { DetailDialogComponent } from './detail-dialog/detail-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    MatTableModule,
    MatPaginatorModule,
    MatListModule,
    MatButtonModule,
    MatSidenavModule,
    MatGridListModule,
    MatSelectModule,
    MatInputModule,
    MatToolbarModule,
    MatIconModule,
    MatDialogModule,
    ReactiveFormsModule,
  ],
  declarations: [
    ConfirmDialogComponent,
    ModifyDialogComponent,
    DetailDialogComponent,
  ],
  exports: [
    CommonModule,
    BrowserAnimationsModule,
    FormsModule,
    MatTableModule,
    MatPaginatorModule,
    HttpClientModule,
    MatListModule,
    MatButtonModule,
    MatSidenavModule,
    MatGridListModule,
    MatSelectModule,
    MatInputModule,
    MatToolbarModule,
    MatIconModule,
    MatDialogModule,
    ConfirmDialogComponent,
    ReactiveFormsModule,
  ],
  entryComponents: [
    ConfirmDialogComponent,
    ModifyDialogComponent,
    DetailDialogComponent,
  ],
})
export class SharedModule { }
