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

  constructor(private chatbotService: ChatbotService) {}

  ngOnInit(): void {
    this.cargarPreguntas();
  }

  cargarPreguntas(): void {
    this.chatbotService.listar().subscribe(data => this.preguntas = data);
  }

  guardar(): void {
    if (this.editando) {
      this.chatbotService.actualizar(this.editando.id!, this.nueva).subscribe(() => {
        this.cancelar();
        this.cargarPreguntas();
      });
    } else {
      this.chatbotService.registrar(this.nueva).subscribe(() => {
        this.cancelar();
        this.cargarPreguntas();
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