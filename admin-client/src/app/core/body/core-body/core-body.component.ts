import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { ModelService } from 'src/app/service/model.service';
import { map } from 'rxjs/operators';
import { MatPaginator, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-core-body',
  templateUrl: './core-body.component.html',
  styleUrls: ['./core-body.component.css']
})

export class CoreBodyComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private service$: ModelService) {}

  displayedColumns: string[];
  dataSource: MatTableDataSource<any[]>;

  ngOnInit() {
    this.service$.getData().pipe(map(x => x.data)).subscribe(val => {
      this.displayedColumns = Object.getOwnPropertyNames(val[0]).slice(0, 3);
      this.displayedColumns.push('actions');
      this.dataSource = new MatTableDataSource<any[]>(val);
      this.dataSource.paginator = this.paginator;
    });
    this.service$.getEntities().subscribe(val => console.log(val));
  }
}
