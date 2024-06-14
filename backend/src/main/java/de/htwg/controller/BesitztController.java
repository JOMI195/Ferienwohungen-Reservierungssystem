package de.htwg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import de.htwg.model.Besitzt;
import de.htwg.repository.BesitztRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BesitztController {

    private final BesitztRepository besitztRepository;
    private final ObjectMapper objectMapper;

    public BesitztController() {
        this.besitztRepository = new BesitztRepository();
        this.objectMapper = new ObjectMapper();
    }

    public void handleGetBesitzt(HttpExchange exchange) throws IOException {
        List<Besitzt> besitztList = besitztRepository.fetchAllBesitzt();
        String responseBody = objectMapper.writeValueAsString(besitztList);
        sendResponse(exchange, 200, responseBody);
    }

    public void handlePostBesitzt(HttpExchange exchange, String requestBody) throws IOException {
        Besitzt besitzt = parseBesitztFromJson(requestBody);
        besitztRepository.insertBesitzt(besitzt);
        String responseBody = objectMapper.writeValueAsString(besitzt);
        sendResponse(exchange, 201, responseBody);
    }

    private Besitzt parseBesitztFromJson(String json) throws IOException {
        return objectMapper.readValue(json, Besitzt.class);
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
