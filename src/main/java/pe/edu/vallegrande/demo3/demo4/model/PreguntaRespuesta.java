package pe.edu.vallegrande.demo3.demo4.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "preguntas_respuestas")
public class PreguntaRespuesta {

    @Id
    private String id;

    private String pregunta;
    private String respuesta;
    private LocalDateTime fechaHora;

    public PreguntaRespuesta() {}

    public PreguntaRespuesta(String pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.fechaHora = LocalDateTime.now();
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}