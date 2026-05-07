package org.example.com.caredate.model.controller;

import org.example.com.caredate.model.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> body) {

        if (body == null || !body.containsKey("mensaje")) {
            return Map.of("respuesta", "Mensaje inválido.");
        }

        String mensaje = body.get("mensaje");

        String respuesta = chatbotService.preguntar(mensaje);

        return Map.of("respuesta", respuesta);
    }
}

