import { Component } from '@angular/core';
import { ChatbotService } from '../../core/services/ChatbotService/chatbot.service';

@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html'
})
export class ChatbotComponent {
  pregunta = '';
  respuesta = '';

  constructor(private chatbotService: ChatbotService) {}

  enviarPregunta() {
    if (this.pregunta.trim()) {
      this.chatbotService.preguntar(this.pregunta).subscribe(res => {
        this.respuesta = res;
      }, err => {
        this.respuesta = 'Ocurrió un error al procesar tu pregunta.';
      });
    }
  }
}