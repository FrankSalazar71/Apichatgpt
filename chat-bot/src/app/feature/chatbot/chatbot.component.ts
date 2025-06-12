import { Component, OnInit } from '@angular/core';
import { ChatbotService } from '../../core/services/chatbot.service';
import { PreguntaRespuesta } from '../../core/interfaces/pregunta-respuesta.interface';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-chatbot',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.css']
})
export class ChatbotComponent implements OnInit {
  preguntas: PreguntaRespuesta[] = [];
  nueva: PreguntaRespuesta = { pregunta: '', respuesta: '', activo: true };
  editando: PreguntaRespuesta | null = null;
  mostrarInactivos: any;

  constructor(private chatbotService: ChatbotService) {}

  ngOnInit(): void {
    this.cargarPreguntas();
  }

  cargarPreguntas(): void {
    this.chatbotService.listar().subscribe(data => this.preguntas = data);
  }

  // Getter para filtrar preguntas según el estado
  get preguntasFiltradas(): PreguntaRespuesta[] {
    return this.mostrarInactivos
      ? this.preguntas.filter(p => !p.activo)
      : this.preguntas.filter(p => p.activo);
  }

  guardar(): void {
  if (this.editando) {
    // 1. Llama a la IA para obtener la nueva respuesta
    this.chatbotService.preguntar(this.nueva.pregunta).subscribe(respuesta => {
      // 2. Actualiza la pregunta y la respuesta en el backend
      const actualizado: PreguntaRespuesta = {
        ...this.nueva,
        respuesta: respuesta
      };
      this.chatbotService.actualizar(this.nueva.id!, actualizado).subscribe(() => {
        this.cargarPreguntas();
        this.cancelar();
      });
    });
  } else {
    // Llama a la IA para obtener la respuesta
    this.chatbotService.preguntar(this.nueva.pregunta).subscribe(respuesta => {
      // Guarda la pregunta y la respuesta en el backend
      const registro: PreguntaRespuesta = {
        pregunta: this.nueva.pregunta,
        respuesta: respuesta,
        activo: true
      };
      this.chatbotService.registrar(registro).subscribe(() => {
        this.cargarPreguntas();
        this.nueva = { pregunta: '', respuesta: '', activo: true };
      });
      // Muestra la respuesta en pantalla
      this.nueva.respuesta = respuesta;
    });
  }
}

  editar(pr: PreguntaRespuesta): void {
    this.editando = pr;
    this.nueva = { ...pr };
  }

  cancelar(): void {
    this.editando = null;
    this.nueva = { pregunta: '', respuesta: '', activo: true };
  }

  eliminar(id: string): void {
    this.chatbotService.eliminar(id).subscribe(() => this.cargarPreguntas());
  }

  restaurar(id: string): void {
    this.chatbotService.restaurar(id).subscribe(() => this.cargarPreguntas());
  }

 

}

