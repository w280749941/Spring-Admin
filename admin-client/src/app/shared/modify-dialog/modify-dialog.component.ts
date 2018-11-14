import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { DialogContent } from 'src/app/domain/DialogContent';
import { Entity } from 'src/app/domain/Entity';

@Component({
  selector: 'app-modify-dialog',
  templateUrl: './modify-dialog.component.html',
  styleUrls: ['./modify-dialog.component.scss']
})
export class ModifyDialogComponent implements OnInit {

  properties: string[];
  entity: any;
  form: FormGroup;
  properyInfo: Entity;

  constructor(private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) private data: DialogContent,
    private dialogRef: MatDialogRef<ModifyDialogComponent>) {
      this.dialogRef.disableClose = true;
      this.dialogRef.backdropClick().subscribe(result => {
        this.dialogRef.close({success: false});
      });
  }

  // TODO add validation for inputs based on types.
  ngOnInit() {
    if (this.data.content === undefined) {
      this.dialogRef.close({success: false});
      return;
    }
    this.entity = this.data.content;
    this.properyInfo = this.data.extra;
    this.properties = Object.getOwnPropertyNames(this.data.content);
    const formGroup = this.properties.reduce((formGroups, property) => {
      formGroups[property] = [this.entity[property], this.getValidator(property)];
      return formGroups;
    }, {});
    this.form = this.fb.group(formGroup);
  }

  onSubmit({value, valid}, ev: Event) {
    ev.preventDefault();
    if (valid) {
      this.dialogRef.close({success: true, data: value});
    }
  }

  private getValidator(property: string): any {
    let pattern: string;
    const type = this.properyInfo.properties.find(x => x.name === property).value;
    if (type === 'Boolean') {
      pattern = '^(true|false)$';
    } else if (type ===  'Number') {
      pattern = '^[0-9]*$';
    }

    return pattern === undefined ? undefined : Validators.pattern(pattern);
  }
}
