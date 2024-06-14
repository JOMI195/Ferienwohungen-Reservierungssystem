package de.htwg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import de.htwg.model.Land;
import de.htwg.repository.LandRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LandController {

    private final LandRepository landRepository;
    private final ObjectMapper objectMapper;

    public LandController() {
        this.landRepository = new LandRepository();
        this.objectMapper = new ObjectMapper();
    }

    public void handleGetLaender(HttpExchange exchange) throws IOException {
        List<Land> laender = landRepository.fetchAllLaender();
        String responseBody = objectMapper.writeValueAsString(laender);
        sendResponse(exchange, 200, responseBody);
    }

    public void handlePostLand(HttpExchange exchange, String requestBody) throws IOException {
        Land land = parseLandFromJson(requestBody);
        landRepository.insertLand(land);
        String responseBody = objectMapper.writeValueAsString(land);
        sendResponse(exchange, 201, responseBody);
    }

    private Land parseLandFromJson(String json) throws IOException {
        return objectMapper.readValue(json, Land.class);
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
