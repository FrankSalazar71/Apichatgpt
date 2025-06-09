import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatbotComponent } from './feature/chatbot/chatbot.component';

const routes: Routes = [
  { path: 'chatbot', component: ChatbotComponent },
  { path: '', redirectTo: '/chatbot', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}