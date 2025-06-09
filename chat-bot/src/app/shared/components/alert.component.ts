import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-alert',
  standalone: true,
  imports: [CommonModule],
  template: `<div class="alert" [ngClass]="cssClass">{{ mensaje }}</div>`
})
export class AlertComponent {
  @Input() mensaje: string = '';
  @Input() tipo: 'success' | 'danger' | 'info' = 'info';

  get cssClass() {
    return {
      'alert-success': this.tipo === 'success',
      'alert-danger': this.tipo === 'danger',
      'alert-info': this.tipo === 'info'
    };
  }
}