package org.example.com.caredate.model.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;

import java.time.Duration;
import java.util.*;

@Service
public class ChatbotService {

    private final String API_KEY = System.getenv("OPENAI_API_KEY");

    private static final int LIMITE = 100;
    private int contadorPreguntas = 0;

    private static final String URL = "https://api.openai.com/v1/responses";

    public String preguntar(String mensajeUsuario) {

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

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(URL, request, Map.class);

            return procesarRespuesta(response, mensajeUsuario);

        } catch (ResourceAccessException e) {
            return "El servicio está tardando demasiado. Intenta de nuevo.";
        } catch (Exception e) {
            return respuestaFallback(mensajeUsuario);
        }
    }

    private Map<String, Object> construirBody(String mensajeUsuario) {
        System.out.println("API KEY: " + API_KEY);
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4.1-mini");
        body.put("max_output_tokens", 100);
        body.put("temperature", 0.5);

        List<Map<String, Object>> input = new ArrayList<>();

        input.add(Map.of(
                "role", "system",
                "content", List.of(
                        Map.of("type", "text",
                                "text", "Eres un asistente médico de CareDate. Responde breve, clara y útil. No des diagnósticos definitivos.")
                )
        ));

        input.add(Map.of(
                "role", "user",
                "content", List.of(
                        Map.of("type", "text", "text", mensajeUsuario)
                )
        ));

        body.put("input", input);

        return body;
    }

    private String procesarRespuesta(ResponseEntity<Map> response, String mensajeUsuario) {

        if (response.getBody() == null) {
            return respuestaFallback(mensajeUsuario);
        }

        Object outputObj = response.getBody().get("output");

        if (!(outputObj instanceof List)) {
            return respuestaFallback(mensajeUsuario);
        }

        List output = (List) outputObj;

        if (output.isEmpty()) {
            return respuestaFallback(mensajeUsuario);
        }

        Object firstObj = output.get(0);

        if (!(firstObj instanceof Map)) {
            return respuestaFallback(mensajeUsuario);
        }

        Map first = (Map) firstObj;

        Object contentObj = first.get("content");

        if (!(contentObj instanceof List)) {
            return respuestaFallback(mensajeUsuario);
        }

        List content = (List) contentObj;

        if (content.isEmpty()) {
            return respuestaFallback(mensajeUsuario);
        }

        Object textObjRaw = content.get(0);

        if (!(textObjRaw instanceof Map)) {
            return respuestaFallback(mensajeUsuario);
        }

        Map textObj = (Map) textObjRaw;

        Object text = textObj.get("text");

        if (text == null) {
            return respuestaFallback(mensajeUsuario);
        }

        return text.toString();
    }

    private RestTemplate crearRestTemplateSeguro() {

        org.springframework.http.client.SimpleClientHttpRequestFactory factory =
                new org.springframework.http.client.SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(5000);
        factory.setReadTimeout(8000);

        return new RestTemplate(factory);
    }

    private String respuestaFallback(String mensaje) {

        mensaje = mensaje.toLowerCase();

        if (mensaje.contains("cita")) {
            return "Puedes agendar una cita desde la sección de citas en la app.";
        }

        if (mensaje.contains("registro")) {
            return "Regístrate ingresando tus datos y luego verifica tu correo.";
        }

        if (mensaje.contains("doctor") || mensaje.contains("medico")) {
            return "Puedes consultar especialistas disponibles desde el apartado de médicos.";
        }

        return "Soy el asistente de CareDate. ¿En qué puedo ayudarte?";
    }
}