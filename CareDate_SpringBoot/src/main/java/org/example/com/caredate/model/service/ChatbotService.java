package org.example.com.caredate.model.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ChatbotService {

    private final String API_KEY = System.getenv("OPENAI_API_KEY");

    private static final int LIMITE = 100;
    private int contadorPreguntas = 0;

    private static final String URL = "https://api.openai.com/v1/responses";

    public String preguntar(String mensajeUsuario) {

        System.out.println("=====================================");
        System.out.println("MENSAJE RECIBIDO: " + mensajeUsuario);
        System.out.println("API KEY NULL? " + (API_KEY == null));

        if (API_KEY != null) {
            System.out.println("API KEY LENGTH: " + API_KEY.length());
        }

        if (API_KEY == null || API_KEY.isBlank()) {
            return "Error de configuración del servidor (API KEY).";
        }

        if (mensajeUsuario == null || mensajeUsuario.trim().isEmpty()) {
            return "Escribe una pregunta válida.";
        }

        if (contadorPreguntas >= LIMITE) {
            return "Has alcanzado el límite de uso del asistente por hoy.";
        }

        contadorPreguntas++;

        try {

            RestTemplate restTemplate = crearRestTemplateSeguro();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(API_KEY);

            Map<String, Object> body = construirBody(mensajeUsuario);

            System.out.println("BODY ENVIADO:");
            System.out.println(body);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(URL, request, Map.class);

            System.out.println("=====================================");
            System.out.println("OPENAI RESPONSE:");
            System.out.println(response.getBody());
            System.out.println("=====================================");

            return procesarRespuesta(response);

        } catch (ResourceAccessException e) {

            e.printStackTrace();

            return "Timeout al conectar con OpenAI.";

        } catch (Exception e) {

            e.printStackTrace();

            return "ERROR: " + e.getMessage();
        }
    }

    private Map<String, Object> construirBody(String mensajeUsuario) {

        Map<String, Object> body = new HashMap<>();

        body.put("model", "gpt-4.1-mini");
        body.put("max_output_tokens", 100);

        List<Map<String, Object>> input = new ArrayList<>();

        input.add(Map.of(
                "role", "system",
                "content", List.of(
                        Map.of(
                                "type", "input_text",
                                "text", "Eres un asistente médico de CareDate. Responde breve, clara y útil. No des diagnósticos definitivos."
                        )
                )
        ));

        input.add(Map.of(
                "role", "user",
                "content", List.of(
                        Map.of(
                                "type", "input_text",
                                "text", mensajeUsuario
                        )
                )
        ));

        body.put("input", input);

        return body;
    }

    private String procesarRespuesta(ResponseEntity<Map> response) {

        Map body = response.getBody();

        if (body == null) {
            return "La respuesta de OpenAI llegó vacía.";
        }

        Object outputObj = body.get("output");

        if (!(outputObj instanceof List<?> output)) {
            return "No se encontró 'output' en la respuesta.";
        }

        for (Object item : output) {

            if (!(item instanceof Map<?, ?> map)) {
                continue;
            }

            Object contentObj = map.get("content");

            if (!(contentObj instanceof List<?> contentList)) {
                continue;
            }

            for (Object content : contentList) {

                if (!(content instanceof Map<?, ?> contentMap)) {
                    continue;
                }

                Object text = contentMap.get("text");

                if (text != null) {
                    return text.toString();
                }
            }
        }

        return "No se encontró texto en la respuesta de OpenAI.";
    }

    private RestTemplate crearRestTemplateSeguro() {

        org.springframework.http.client.SimpleClientHttpRequestFactory factory =
                new org.springframework.http.client.SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(5000);
        factory.setReadTimeout(10000);

        return new RestTemplate(factory);
    }
}