import { Component, OnInit } from '@angular/core';



export interface Food {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-core-body',
  templateUrl: './core-body.component.html',
  styleUrls: ['./core-body.component.css']
})


export class CoreBodyComponent implements OnInit {

  foods: Food[] = [
    {value: 'steak-0', viewValue: 'Steak'},
    {value: 'pizza-1', viewValue: 'Pizza'},
    {value: 'tacos-2', viewValue: 'Tacos'}
  ];
  constructor() { }

  ngOnInit() {
  }

}
