import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PreguntaRespuesta } from '../../interfaces/PreguntaRespuesta';

@Injectable({ providedIn: 'root' })
export class ChatbotService {
  private baseUrl = 'http://localhost:8080/api/chatbot';

  constructor(private http: HttpClient) {}

  listar(): Observable<PreguntaRespuesta[]> {
    return this.http.get<PreguntaRespuesta[]>(this.baseUrl);
  }

  obtener(id: string): Observable<PreguntaRespuesta> {
    return this.http.get<PreguntaRespuesta>(`${this.baseUrl}/${id}`);
  }

  registrar(data: PreguntaRespuesta): Observable<PreguntaRespuesta> {
    return this.http.post<PreguntaRespuesta>(this.baseUrl, data);
  }

  actualizar(id: string, data: PreguntaRespuesta): Observable<PreguntaRespuesta> {
    return this.http.put<PreguntaRespuesta>(`${this.baseUrl}/${id}`, data);
  }

  eliminar(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  restaurar(id: string): Observable<PreguntaRespuesta> {
    return this.http.put<PreguntaRespuesta>(`${this.baseUrl}/restaurar/${id}`, {});
  }

  preguntar(pregunta: string): Observable<string> {
    return this.http.post(`${this.baseUrl}/preguntar`, { pregunta }, { responseType: 'text' });
  }
}
