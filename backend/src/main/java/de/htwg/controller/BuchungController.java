package de.htwg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;

import de.htwg.model.Buchung;
import de.htwg.repository.BuchungRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;

public class BuchungController {

    private final BuchungRepository buchungRepository;
    private final ObjectMapper objectMapper;

    public BuchungController() {
        this.buchungRepository = new BuchungRepository();
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    public void handleGetBuchungen(HttpExchange exchange) throws IOException {
        List<Buchung> buchungen = buchungRepository.fetchAllBuchungen();
        String responseBody = objectMapper.writeValueAsString(buchungen);
        sendResponse(exchange, 200, responseBody);
    }

    public void handlePostBuchung(HttpExchange exchange, String requestBody) throws IOException {
        Buchung buchung = parseBuchungFromJson(requestBody);
        buchungRepository.insertBuchung(buchung);
        String responseBody = objectMapper.writeValueAsString(buchung);
        sendResponse(exchange, 201, responseBody);
    }

    private Buchung parseBuchungFromJson(String json) throws IOException {
        return objectMapper.readValue(json, Buchung.class);
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
