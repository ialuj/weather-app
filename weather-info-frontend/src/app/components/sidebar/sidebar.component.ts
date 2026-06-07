import { Component, EventEmitter, Output, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';

export type MenuOption = 'cities' | 'location' | 'others';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent {
  @Output() menuChange = new EventEmitter<MenuOption>();

  activeMenu: MenuOption = 'cities';
  isOpen = false;
  isMobile = false;

  ngOnInit() {
    this.checkScreenSize();
  }

  @HostListener('window:resize')
  checkScreenSize() {
    this.isMobile = window.innerWidth <= 768;
    if (!this.isMobile) {
      this.isOpen = false;
    }
  }

  setActive(option: MenuOption) {
    this.activeMenu = option;
    this.menuChange.emit(option);
    if (this.isMobile) {
      this.isOpen = false;
    }
  }

  toggleSidebar() {
    this.isOpen = !this.isOpen;
  }

  closeSidebar() {
    if (this.isMobile) {
      this.isOpen = false;
    }
  }
}
