package pe.edu.vallegrande.demo3.demo4.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.demo3.demo4.api.ValleGrandeApiClient;

import java.util.Map;

@Service
public class IAResponderService {

    @Autowired
    private ValleGrandeApiClient apiClient;

    public String responder(String preguntaUsuario) {
        String pregunta = preguntaUsuario.toLowerCase();

        Map<String, String> datos = apiClient.obtenerDatos();

        if (pregunta.contains("carreras")) {
            return datos.get("carreras");
        } else if (pregunta.contains("examen") || pregunta.contains("admisión")) {
            return datos.get("admision_fechas");
        } else if (pregunta.contains("semestre") || pregunta.contains("inicio")) {
            return datos.get("inicio_semestre");
        } else if (pregunta.contains("matrícula")) {
            return datos.get("matricula_costo");
        } else if (pregunta.contains("mensualidad") || pregunta.contains("pensión")) {
            return datos.get("mensualidad_costo");
        } else if (pregunta.contains("clases") && (pregunta.contains("virtual") || pregunta.contains("presencial"))) {
            return datos.get("modalidad_clases");
        } else if (pregunta.contains("celular")) {
            return datos.get("desde_celular");
        } else if (pregunta.contains("requisito") || pregunta.contains("equipo")) {
            return datos.get("requisitos_equipo");
        } else if (pregunta.contains("cronograma")) {
            return datos.get("cronograma_pagos");
        } else {
            return "Lo siento, no tengo información para esa pregunta aún. ¿Podrías reformularla?";
        }
    }
}
