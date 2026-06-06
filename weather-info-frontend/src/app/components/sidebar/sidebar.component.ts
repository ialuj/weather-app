import { Component, EventEmitter, Output } from '@angular/core';

export type MenuOption = 'cities' | 'location' | 'others';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
})
export class SidebarComponent {
  @Output() menuChange = new EventEmitter<MenuOption>();
  activeMenu: MenuOption = 'cities';

  setActive(option: MenuOption) {
    this.activeMenu = option;
    this.menuChange.emit(option);
  }
}
