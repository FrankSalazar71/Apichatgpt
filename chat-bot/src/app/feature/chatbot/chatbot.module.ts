// feature/chatbot/chatbot.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChatbotComponent } from './chatbot.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [ChatbotComponent],
  imports: [
    CommonModule,
    FormsModule
  ]
})
export class ChatbotModule {}
