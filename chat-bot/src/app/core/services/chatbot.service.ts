import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ChatbotService {
  private apiUrl = 'http://localhost:8080/api/chatbot';

  constructor(private http: HttpClient) {}

  enviarPregunta(pregunta: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/preguntar`, { pregunta });
  }

  listar(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }

  guardar(pregunta: string, respuesta: string): Observable<any> {
    return this.http.post(`${this.apiUrl}`, { pregunta, respuesta });
  }

  eliminar(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
