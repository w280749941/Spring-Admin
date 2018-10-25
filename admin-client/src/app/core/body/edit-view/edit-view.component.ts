import { Component, OnInit } from '@angular/core';
import { Data } from '../Data';

@Component({
  selector: 'app-edit-view',
  templateUrl: './edit-view.component.html',
  styleUrls: ['./edit-view.component.scss']
})

export class EditViewComponent implements OnInit {

  properties: string[];
  entity: any;

  constructor(private data: Data) {

  }

  ngOnInit() {
    if (this.data.storage === undefined) {
      return;
    }
    this.entity = this.data.storage;
    this.properties = Object.getOwnPropertyNames(this.data.storage);
  }
}
