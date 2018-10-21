import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { of } from 'rxjs';
import { delay, map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Result } from '../domain/Result.model';

@Injectable({
  providedIn: 'root'
})
export class ModelService {
  constructor(private http: HttpClient) {}

  getColumns(): string[] {
    return ['roleId', 'roleName', 'isDeleted', 'isActive'];
  }

  getModelData(): Observable<any[]> {
    return of(CHARACTERS);
  }

  getModelDataAsync(): Observable<any[]> {
    return of(CHARACTERS).pipe(delay(1700));
  }

  getData(): Observable<Result> {
    return this.http.get<Result>('http://localhost:8080/admin/role/'); // .pipe(map(val => val['data']));
  }
}

export const CHARACTERS: any[] = [
  {
    roleId: 1,
    roleName: 'asb',
    isDeleted: false,
    isActive: true
  },
  {
    roleId: 7,
    roleName: 'user',
    isDeleted: false,
    isActive: true
  },
  {
    roleId: 27,
    roleName: 'BigBooty',
    isDeleted: false,
    isActive: true
  },
  {
    roleId: 30,
    roleName: 'bad Role',
    isDeleted: false,
    isActive: false
  },
  {
    roleId: 33,
    roleName: 'new Role3',
    isDeleted: true,
    isActive: false
  },
  {
    roleId: 42,
    roleName: 'ok Role',
    isDeleted: true,
    isActive: false
  }
];

export const OTHERS: any[] =
[
  {
    name: 'Earl of Lemongrab',
    age: 'Unknown',
    species: 'Lemon Candy',
    occupation: 'Earl, Heir to the Candy Kingdom Throne'
  },
  {
    name: 'Bonnibel Bubblegum',
    age: '19',
    species: 'Gum Person',
    occupation: 'Returned Ruler of the Candy Kingdom'
  },
  {
    name: 'Phoebe',
    age: '16',
    species: 'Flame Person',
    occupation: 'Ruler of the Fire Kingdom'
  },
  {
    name: 'Lumpy Space Princess',
    age: '18',
    species: 'Lumpy Space Person',
    occupation: 'Babysitter'
  },
];
