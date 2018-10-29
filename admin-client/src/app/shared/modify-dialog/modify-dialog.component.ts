import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-modify-dialog',
  templateUrl: './modify-dialog.component.html',
  styleUrls: ['./modify-dialog.component.scss']
})
export class ModifyDialogComponent implements OnInit {

  properties: string[];
  entity: any;
  form: FormGroup;

  constructor(private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private data: any,
    private dialogRef: MatDialogRef<ModifyDialogComponent>) {
  }

  ngOnInit() {
    if (this.data === undefined) {
      this.dialogRef.close();
      return;
    }
    this.entity = this.data;
    this.properties = Object.getOwnPropertyNames(this.data);
    const formGroup = this.properties.reduce((formGroups, property) => {
      formGroups[property] = [this.entity[property]];
      return formGroups;
    }, {});
    this.form = this.fb.group(formGroup);
  }

  onSubmit({value, valid}, ev: Event) {
    ev.preventDefault();
    console.log(JSON.stringify(value));
    console.log(valid);
    this.dialogRef.close(true);
  }
}
