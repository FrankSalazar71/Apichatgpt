package pe.edu.vallegrande.demo3.demo4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.demo3.demo4.service.IAResponderService;

/**
@Controller
public class WebController {

    @Autowired
    private IAResponderService responderService;

 @GetMapping("/")
    public String mostrarFormulario() {
        return "bot";
    }

    @PostMapping("/preguntar")
    public String procesarPregunta(@RequestParam("pregunta") String pregunta, Model model) {
        String respuesta = responderService.responder(pregunta);
        model.addAttribute("pregunta", pregunta);
        model.addAttribute("respuesta", respuesta);
        return "bot";
    }
}
 **/