import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-detail-dialog',
  templateUrl: './detail-dialog.component.html',
  styleUrls: ['./detail-dialog.component.scss']
})
export class DetailDialogComponent implements OnInit {

  properties: string[];
  entity: any;

  constructor(@Inject(MAT_DIALOG_DATA) private data: any) {

  }

  ngOnInit() {
    if (this.data === undefined) {
      return;
    }
    this.entity = this.data;
    this.properties = Object.getOwnPropertyNames(this.data);
  }
}
