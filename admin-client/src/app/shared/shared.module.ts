import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { MatTableModule, MatPaginatorModule, MatButtonModule } from '@angular/material';
import { HttpClientModule } from '@angular/common/http';
import { MatListModule } from '@angular/material/list';

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
  ],
  declarations: [],
  exports: [
    CommonModule,
    BrowserAnimationsModule,
    FormsModule,
    MatTableModule,
    MatPaginatorModule,
    HttpClientModule,
    MatListModule,
    MatButtonModule,
  ]
})
export class SharedModule { }
