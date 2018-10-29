import { Component, OnInit, ViewChild } from '@angular/core';
import { ModelService } from 'src/app/service/model.service';
import { map } from 'rxjs/operators';
import { MatPaginator, MatTableDataSource, MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { Data } from '../Data';
import { ConfirmDialogComponent } from 'src/app/shared/confirm-dialog/confirm-dialog.component';


interface EntityInfo {
  entity: string;
  id: string;
}

@Component({
  selector: 'app-core-body',
  templateUrl: './core-body.component.html',
  styleUrls: ['./core-body.component.scss']
})

export class CoreBodyComponent implements OnInit {
  @ViewChild(MatPaginator)
  paginator: MatPaginator;

  constructor(private service$: ModelService, private router: Router, private data: Data, public dialog: MatDialog) {}

  displayedColumns: string[];
  dataSource: MatTableDataSource<any[]>;
  selected = 'None';
  entityNames: string[];

  entitiesInfo: EntityInfo[];

  ngOnInit() {
    this.service$
      .getEntities()
      .subscribe(val => {
        this.entityNames = val.map(x => x.name);
        this.entitiesInfo = val.map(x => {
            const entityInfo = {} as EntityInfo;
            entityInfo.entity = x.name;
            entityInfo.id = x.idName;
            return entityInfo;
          });
      });
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
      const entityInfo = this.entitiesInfo.find(x => x.entity === this.selected) as EntityInfo;
      const dialogRef = this.dialog.open(ConfirmDialogComponent, {
        data: {
          property: entityInfo.id,
          id: data[entityInfo.id]
        }
      });
      dialogRef.afterClosed().subscribe(result => console.log(result));
      this.service$.deleteEntity(data);
    }
  }
}
