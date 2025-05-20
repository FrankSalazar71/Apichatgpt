package pe.edu.vallegrande.demo3.demo4.api;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ValleGrandeApiClient {

    public Map<String, String> obtenerDatos() {
        // Simulación de datos de una API real
        return Map.of(
                "carreras", "Analisis de sistemas empresariales, Gestion agricola .",
                "admision_fechas", "Los exámenes de admisión se darán el 25 de febrero y el 5 de marzo.",
                "inicio_semestre", "El semestre inicia el 3 de marzo.",
                "matricula_costo", "El costo de matrícula es de S/ 230.",
                "mensualidad_costo", "La mensualidad es de S/ 430",
                "modalidad_clases", "Las clases son virtuales, con apoyo presencial.",
                "desde_celular", "Sí, puede ingresar y participar desde su celular, aunque se recomienda laptop.",
                "requisitos_equipo", "Como mínimo: 4GB de RAM, procesador i3 o superior, cámara y micrófono.",
                "cronograma_pagos", "El cronograma de pagos incluye 5 cuotas mensuales luego de la matrícula."
        );
    }
}