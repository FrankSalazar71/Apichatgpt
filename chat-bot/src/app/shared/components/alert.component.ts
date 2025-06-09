import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-alert',
  template: `<div *ngIf="mensaje" class="alert alert-warning">{{ mensaje }}</div>`
})
export class AlertComponent {
  @Input() mensaje = '';
}
