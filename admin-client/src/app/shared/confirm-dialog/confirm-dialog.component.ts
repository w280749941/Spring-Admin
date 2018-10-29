import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { DialogData } from 'src/app/domain/DialogData';

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.scss']
})
export class ConfirmDialogComponent implements OnInit {

  property = '';
  id = '';

  constructor(@Inject(MAT_DIALOG_DATA) private data: DialogData, private dialogRef: MatDialogRef<ConfirmDialogComponent>) { }

  ngOnInit() {
    this.property = this.data.property;
    this.id = this.data.id;
  }

  onClick(result: Boolean) {
    this.dialogRef.close(result);
  }
}
