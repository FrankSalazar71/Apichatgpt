package pe.edu.vallegrande.demo3.demo4.controller;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.jsoup.nodes.Document;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pe.edu.vallegrande.demo3.demo4.model.PreguntaRespuesta;
import pe.edu.vallegrande.demo3.demo4.repository.PreguntaRespuestaRepository;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@Controller
public class BotController {

    private final PreguntaRespuestaRepository repository;

    @Value("${api.rapidapi.url}")
    private String apiUrl;

    @Value("${api.rapidapi.key}")
    private String apiKey;

    private static final String LINK_VALLE_GRANDE = "https://vallegrande.edu.pe";

    public BotController(PreguntaRespuestaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String mostrarFormulario() {
        return "bot";
    }

    @PostMapping("/preguntar")
    public String procesarPregunta(@RequestParam("pregunta") String pregunta, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String contenidoWeb = "";

        try {
            Document doc = Jsoup.connect(LINK_VALLE_GRANDE).get();
            contenidoWeb = doc.body().text();
            if (contenidoWeb.length() > 6000) {
                contenidoWeb = contenidoWeb.substring(0, 6000) + "...";
            }
            System.out.println("✅ Texto extraído del sitio web:");
            System.out.println(contenidoWeb);
        } catch (Exception e) {
            System.out.println("🚫 Error al extraer contenido del enlace: " + e.getMessage());
        }

        String contextoFijo = """
            Actúa como un asistente experto del Instituto Valle Grande.

            Valle Grande es un Instituto de Educación Superior ubicado en Cañete, Perú. Ofrece carreras como Gestión Agraria y Análisis de Sistemas Empresarial, las clases son de lunes a viernes.

            La matrícula tiene un costo de S/ 200, si presenta demoras seria S/ 220 0 230, y la pensión mensual es de S/ 430.
            Las clases son virtuales, pero con soporte presencial.
            Se recomienda que los estudiantes usen laptops o PCs con conexión estable a internet.
            Aunque es posible usar un celular para algunas tareas, no es adecuado para llevar las clases completas.

            Laptop o computadora de escritorio con: procesador Intel Core i5 o AMD Ryzen 5, 8 a 16Gb RAM DDR4 y 240Gb SSD. Si es de escritorio, usar un UPS.
            Accesorios para clases virtuales: micrófono y cámara (pueden estar incorporados).

            Los exámenes de admisión se realizan en junio y julio. 
            El semestre académico inicia en agosto. 
            El cronograma de pagos incluye matrícula al inicio del semestre y mensualidades consecutivas hasta completar el ciclo.
            La justificación en Valle Grande se puede realizar por medio del Q10.
            La vestimenta es formal y si es con el polo del instituto mejor.
            El instituto busca ofrecer una educación práctica, técnica y con orientación al trabajo.
        """ + contenidoWeb;

        String fragmentoRelevante = "";
        if (pregunta.toLowerCase().contains("ubicación") || pregunta.toLowerCase().contains("dónde está") || pregunta.toLowerCase().contains("dirección")) {
            for (String linea : contenidoWeb.split("\\. ")) {
                if (linea.toLowerCase().contains("panamericana") || linea.toLowerCase().contains("san vicente") || linea.toLowerCase().contains("cañete") || linea.toLowerCase().contains("contacto")) {
                    fragmentoRelevante += linea.trim() + ". ";
                }
            }
        }

        String contextoFinal = contextoFijo;
        if (!fragmentoRelevante.isEmpty()) {
            contextoFinal += "\n\n📌 Información relacionada del sitio web oficial:\n" + fragmentoRelevante;
        }

        Map<String, Object> request = new HashMap<>();
        request.put("messages", new Object[] {
            Map.of("role", "system", "content", contextoFinal),
            Map.of("role", "user", "content", pregunta)
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-evocortexai-key", apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        String respuestaGenerada = "";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String body = response.getBody();
                StringBuilder contenidoFinal = new StringBuilder();

                if (body != null) {
                    String[] lineas = body.split("data:");
                    ObjectMapper objectMapper = new ObjectMapper();

                    for (String linea : lineas) {
                        linea = linea.trim();
                        if (linea.isEmpty() || linea.equals("[DONE]")) continue;

                        try {
                            JsonNode nodo = objectMapper.readTree(linea);
                            if (nodo.has("choices")) {
                                JsonNode delta = nodo.get("choices").get(0).get("delta");
                                if (delta != null && delta.has("content")) {
                                    contenidoFinal.append(delta.get("content").asText());
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("⚠️ Error parseando línea: " + linea);
                        }
                    }
                }

                respuestaGenerada = contenidoFinal.toString();
                model.addAttribute("respuesta", respuestaGenerada);
            } else {
                respuestaGenerada = "⚠️ Error al obtener respuesta del servidor externo.";
                model.addAttribute("respuesta", respuestaGenerada);
            }
        } catch (Exception e) {
            respuestaGenerada = "🚫 No se pudo conectar con el API externo.";
            model.addAttribute("respuesta", respuestaGenerada);
            System.out.println("Error: " + e.getMessage());
        }

        PreguntaRespuesta registro = new PreguntaRespuesta(pregunta, respuestaGenerada);
        repository.save(registro);

        model.addAttribute("pregunta", pregunta);
        return "bot";
    }
}
