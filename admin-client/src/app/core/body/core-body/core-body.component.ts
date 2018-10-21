import { Component, OnInit } from '@angular/core';
import { ModelService } from 'src/app/service/model.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

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

  constructor(private service$: ModelService) {}

  originalData: Observable<any[]>;
  displayedColumns: string[];
  asyncData: Observable<any[]>;
  RealData: Observable<any[]>;

  ngOnInit() {
    this.RealData = this.service$.getData().pipe(map(x => x.data));
    this.RealData.subscribe(v => {
      this.displayedColumns = Object.getOwnPropertyNames(v[0]);
    });
  }
}
export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}
