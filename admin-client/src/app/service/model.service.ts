import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { of } from 'rxjs';
import { delay, map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Result } from '../domain/Result.model';
import { Entity, Property } from '../domain/Entity';

@Injectable({
  providedIn: 'root'
})
export class ModelService {
  constructor(private http: HttpClient) {}

  getData(): Observable<Result> {
    return this.http.get<Result>('http://localhost:8080/admin/role/'); // .pipe(map(val => val['data']));
  }

  getEntities(): Observable<Entity[]> {
    return this.http.get<Result>('http://localhost:8080/admin/properties/').pipe(map(x => {
      const entities = [] as Entity[];
      const response = x.data;
      const entitieNames = Object.getOwnPropertyNames(response);
      entitieNames.forEach(entityName => {
        const serverEntity = response[entityName];
        const entity = {} as Entity;
        entity.name = entityName;
        entity.idName = serverEntity[0]['id'];
        const propertyList = [] as Property[];
        const properties = Object.getOwnPropertyNames(serverEntity[1]);
        properties.forEach(y => {
          const property = {} as Property;
          property.name = y;
          property.value = serverEntity[1][y];
          propertyList.push(property);
        });
        entity.properties = propertyList;
        entities.push(entity);
      });
      return entities;
    }));
  }

  getPropertiess() {
    this.http.get<Result>('http://localhost:8080/admin/properties/').pipe(map(x => x.data)).subscribe(val => {
      const entitieNames = Object.getOwnPropertyNames(val);
      entitieNames.forEach(x => {
        const entity = val[x];
        console.log(entity);
        console.log('id: ' + entity[0]['id']);
        const properties = Object.getOwnPropertyNames(entity[1]);
        const dict = {};
        properties.forEach(y => {
          console.log(y);
          dict[y] = entity[1][y];
        });
        console.log('My dictionary: ' + dict['isDeleted']);
      });
    });
  }
}

