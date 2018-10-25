import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-core-header',
  templateUrl: './core-header.component.html',
  styleUrls: ['./core-header.component.scss']
})
export class CoreHeaderComponent implements OnInit {

  @Output() toggleSideNav = new EventEmitter<void>();

  constructor() { }

  ngOnInit() {
  }

  private sideBarToggle() {
    this.toggleSideNav.emit();
  }
}
