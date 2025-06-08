import { Component } from '@angular/core';
import { ChatbotService } from 'src/app/core/services/chatbot.service.ts';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html'
})
export class ChatComponent {
  pregunta = '';
  respuesta = '';
  historial: any[] = [];

  constructor(private chatService: ChatbotService) {
    this.cargarHistorial();
  }

  enviar() {
    this.chatService.enviarPregunta(this.pregunta).subscribe((res: string) => {
      this.respuesta = res;
      this.chatService.guardar(this.pregunta, res).subscribe();
      this.cargarHistorial();
    });
  }

  cargarHistorial() {
    this.chatService.listar().subscribe((data: any[]) => {
      this.historial = data;
    });
  }

  eliminar(id: string) {
    this.chatService.eliminar(id).subscribe(() => {
      this.cargarHistorial();
    });
  }
}