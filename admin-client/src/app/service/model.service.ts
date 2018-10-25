import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Result } from '../domain/Result.model';
import { Entity, Property } from '../domain/Entity';

@Injectable({
  providedIn: 'root'
})
export class ModelService {
  constructor(private http: HttpClient) {}

  getData(entity: string): Observable<Result> {
    return this.http.get<Result>(`http://localhost:8080/admin/entity/${entity}/`); // .pipe(map(val => val['data']));
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

  createEntity(entity: any) {
    console.log(`Sending create request to server: ${entity}`);
  }

  updateEntity(entity: any) {
    console.log(`Sending update request to server: ${entity}`);
  }

  deleteEntity(entity: any) {
    console.log(`Sending delete request to server: ${entity}`);
  }
}

