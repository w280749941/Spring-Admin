import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoreBodyComponent } from './core-body.component';

describe('CoreBodyComponent', () => {
  let component: CoreBodyComponent;
  let fixture: ComponentFixture<CoreBodyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoreBodyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoreBodyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
