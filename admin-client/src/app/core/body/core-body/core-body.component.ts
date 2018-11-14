import { Component, OnInit, ViewChild } from '@angular/core';
import { ModelService } from 'src/app/service/model.service';
import { map } from 'rxjs/operators';
import { MatPaginator, MatTableDataSource, MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { Data } from '../Data';
import { ConfirmDialogComponent } from 'src/app/shared/confirm-dialog/confirm-dialog.component';
import { ModifyDialogComponent } from 'src/app/shared/modify-dialog/modify-dialog.component';
import { DetailDialogComponent } from 'src/app/shared/detail-dialog/detail-dialog.component';
import { Entity } from 'src/app/domain/Entity';
import { DialogContent } from 'src/app/domain/DialogContent';


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
  propertiesInfo: Entity[];
  selectedPropertyInfo: {};

  ngOnInit() {
    this.service$
      .getEntities()
      .subscribe(val => {
        this.propertiesInfo = val;
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
      this.selected = 'None';
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

    this.selectedPropertyInfo =  this.propertiesInfo
                                  .filter(x => x.name === this.selected)[0].properties
                                  .reduce((emptyEntity, property) => {
                                    emptyEntity[property.name] = '';
                                    return emptyEntity;
                                  }, {});
  }

  onClick(data: any, input: any) {
    // this.data.storage = data;
    if (input === 'Edit') {
      const dialogRef = this.dialog.open(ModifyDialogComponent, {
        data: {
          content: data,
          extra: this.propertiesInfo.find(x => x.name === this.selected)
        } as DialogContent
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result.success) {
          this.service$.updateEntity({
            entity: this.selected,
            body: result.data
          }).subscribe(
            this.onSuccess,
            this.onError,
            this.onComplete
          );
        }
      });
    } else if (input === 'Detail') {
      this.dialog.open(DetailDialogComponent, {data: data});
    } else if (input === 'Delete') {
      const entityInfo = this.entitiesInfo.find(x => x.entity === this.selected) as EntityInfo;
      const dialogRef = this.dialog.open(ConfirmDialogComponent, {
        data: {
          property: entityInfo.id,
          id: data[entityInfo.id]
        }
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result === true) {
          this.service$.deleteEntity({
            entity: this.selected,
            id: data[entityInfo.id]
          }).subscribe(
            this.onSuccess,
            this.onError,
            this.onComplete
          );
        }
      });
    } else if (input === 'New') {
      const dialogRef = this.dialog.open(ModifyDialogComponent, {
        data: {
          content: this.selectedPropertyInfo,
          extra: this.propertiesInfo.find(x => x.name === this.selected)
        } as DialogContent
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result.success) {
          this.service$.createEntity({
            entity: this.selected,
            body: result.data
          }).subscribe(
            this.onSuccess,
            this.onError,
            this.onComplete
          );
        }
      });
    }
  }

  onSuccess = val => {
    console.log('Update Successful: ' + JSON.stringify(val));
    this.onSelect();
  }

  onError = err => {
    console.log('Update error: ' + JSON.stringify(err));
  }

  onComplete = () => {
    console.log('Completed');
  }
}
