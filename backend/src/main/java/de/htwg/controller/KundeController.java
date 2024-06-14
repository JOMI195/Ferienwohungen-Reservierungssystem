package de.htwg.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import de.htwg.model.Kunde;
import de.htwg.repository.KundeRepository;

public class KundeController {

    private KundeRepository kundeRepository;
    private ObjectMapper objectMapper;

    public KundeController() {
        this.kundeRepository = new KundeRepository();
        this.objectMapper = new ObjectMapper();
    }

    public void handleGetKunden(HttpExchange exchange) throws IOException {
        List<Kunde> kunden = kundeRepository.fetchAllKunden();
        String responseBody = objectMapper.writeValueAsString(kunden);
        sendResponse(exchange, 200, responseBody);
    }

    public void handlePostKunde(HttpExchange exchange, String requestBody) throws IOException {
        Kunde kunde = parseKundeFromJson(requestBody);
        kundeRepository.insertKunde(kunde);
        String responseBody = objectMapper.writeValueAsString(kunde);
        sendResponse(exchange, 201, responseBody);
    }

    private Kunde parseKundeFromJson(String json) throws IOException {
        return objectMapper.readValue(json, Kunde.class);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String responseBody) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBody.getBytes(StandardCharsets.UTF_8).length);
        OutputStream out = exchange.getResponseBody();
        out.write(responseBody.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
