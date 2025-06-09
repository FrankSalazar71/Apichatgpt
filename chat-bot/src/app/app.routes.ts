import { Routes } from '@angular/router';
import { ChatbotComponent } from './feature/chatbot/chatbot.component';

export const routes: Routes = [
  { path: 'chatbot', component: ChatbotComponent },
  { path: '', redirectTo: '/chatbot', pathMatch: 'full' }
];