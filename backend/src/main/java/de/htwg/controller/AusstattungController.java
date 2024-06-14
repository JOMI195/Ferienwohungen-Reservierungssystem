package de.htwg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import de.htwg.model.Ausstattung;
import de.htwg.repository.AusstattungRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AusstattungController {

    private final AusstattungRepository ausstattungRepository;
    private final ObjectMapper objectMapper;

    public AusstattungController() {
        this.ausstattungRepository = new AusstattungRepository();
        this.objectMapper = new ObjectMapper();
    }

    public void handleGetAusstattungen(HttpExchange exchange) throws IOException {
        List<Ausstattung> ausstattungen = ausstattungRepository.fetchAllAusstattungen();
        String responseBody = objectMapper.writeValueAsString(ausstattungen);
        sendResponse(exchange, 200, responseBody);
    }

    public void handlePostAusstattung(HttpExchange exchange, String requestBody) throws IOException {
        Ausstattung ausstattung = parseAusstattungFromJson(requestBody);
        ausstattungRepository.insertAusstattung(ausstattung);
        String responseBody = objectMapper.writeValueAsString(ausstattung);
        sendResponse(exchange, 201, responseBody);
    }

    private Ausstattung parseAusstattungFromJson(String json) throws IOException {
        return objectMapper.readValue(json, Ausstattung.class);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String responseBody) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBody.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(responseBody.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }
}
