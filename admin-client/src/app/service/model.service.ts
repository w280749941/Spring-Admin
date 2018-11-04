import { Injectable, Inject } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Result } from '../domain/Result.model';
import { Entity, Property } from '../domain/Entity';

@Injectable({
  providedIn: 'root'
})
export class ModelService {

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
    })
  };
  constructor(private http: HttpClient, @Inject('BASE_URL') private remoteUrl: string) {}

  getData(entity: string): Observable<Result> {
    return this.http.get<Result>(`${this.remoteUrl}/entity/${entity}/`); // .pipe(map(val => val['data']));
  }

  getEntities(): Observable<Entity[]> {
    return this.http.get<Result>(`${this.remoteUrl}/properties/`).pipe(map(x => {
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

  createEntity(data: any) {
    const url = `${this.remoteUrl}/entity/${data.entity}`;
    console.log(`Sending create request to server ${url}`);
    return this.http.post(url, JSON.stringify(data.body), this.httpOptions);
  }

  updateEntity(data: any) {
    const url = `${this.remoteUrl}/entity/${data.entity}`;
    console.log(`Sending update request to server ${url}`);
    return this.http.put(url, JSON.stringify(data.body), this.httpOptions);
  }

  deleteEntity(data: any) {
    const url = `${this.remoteUrl}/entity/${data.entity}/${data.id}`;
    console.log(`Sending delete request to server: ${url}`);
    return this.http.delete(url);
  }
}

