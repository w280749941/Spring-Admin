import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { ModelService } from 'src/app/service/model.service';
import { map } from 'rxjs/operators';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { Router, NavigationExtras } from '@angular/router';
import { Data } from '../Data';

@Component({
  selector: 'app-core-body',
  templateUrl: './core-body.component.html',
  styleUrls: ['./core-body.component.scss']
})

export class CoreBodyComponent implements OnInit {
  @ViewChild(MatPaginator)
  paginator: MatPaginator;

  constructor(private service$: ModelService, private router: Router, private data: Data) {}

  displayedColumns: string[];
  dataSource: MatTableDataSource<any[]>;
  selected = 'None';
  entityNames: string[];

  ngOnInit() {
    this.service$
      .getEntities()
      .subscribe(val => (this.entityNames = val.map(x => x.name)));
  }

  onSelect() {
    if (this.selected === 'None' || this.selected === undefined) {
      this.dataSource = null;
      return;
    }
    this.service$
      .getData(this.selected)
      .pipe(map(x => x.data))
      .subscribe(val => {
        this.displayedColumns = Object.getOwnPropertyNames(val[0]).slice(0, 3);
        this.displayedColumns.push('actions');
        this.dataSource = new MatTableDataSource<any[]>(val);
        this.dataSource.paginator = this.paginator;
      });
  }

  onClick(data: any, input: any) {
    this.data.storage = data;
    if (input === 'Edit') {
      this.router.navigate(['edit']);
    } else if (input === 'Detail') {
      this.router.navigate(['detail']);
    } else if (input === 'Delete') {
      alert('Deleting an entity');
      this.service$.deleteEntity(data);
    }
  }
}
