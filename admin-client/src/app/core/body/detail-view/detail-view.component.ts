import { Component, OnInit } from '@angular/core';
import { Data } from '../Data';

@Component({
  selector: 'app-detail-view',
  templateUrl: './detail-view.component.html',
  styleUrls: ['./detail-view.component.css']
})
export class DetailViewComponent implements OnInit {

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
