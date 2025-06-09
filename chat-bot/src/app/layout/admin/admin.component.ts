import { Component } from '@angular/core';
import { ChatbotComponent } from '../../feature/chatbot/chatbot.component';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [ChatbotComponent],
  templateUrl: './admin.component.html'
})
export class AdminComponent {}