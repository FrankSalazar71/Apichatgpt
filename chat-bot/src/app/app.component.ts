import { Component } from '@angular/core';
import { AdminComponent } from './layout/admin/admin.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [AdminComponent],
  template: `<app-admin></app-admin>`
})
export class AppComponent {}