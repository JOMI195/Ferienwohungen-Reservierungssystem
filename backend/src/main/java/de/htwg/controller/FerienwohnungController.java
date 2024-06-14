package de.htwg.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import de.htwg.model.Ferienwohnung;
import de.htwg.repository.FerienwohnungRepository;

public class FerienwohnungController {

    private FerienwohnungRepository ferienwohnungRepository;
    private ObjectMapper objectMapper;

    public FerienwohnungController() {
        this.ferienwohnungRepository = new FerienwohnungRepository();
        this.objectMapper = new ObjectMapper();
    }

    public void handleGetFerienwohnungen(HttpExchange exchange) throws IOException {
        List<Ferienwohnung> ferienwohnungen = ferienwohnungRepository.fetchAllFerienwohnungen();
        String responseBody = objectMapper.writeValueAsString(ferienwohnungen);
        sendResponse(exchange, 200, responseBody);
    }

    public void handlePostFerienwohnung(HttpExchange exchange, String requestBody) throws IOException {
        Ferienwohnung ferienwohnung = parseFerienwohnungFromJson(requestBody);
        ferienwohnungRepository.insertFerienwohnung(ferienwohnung);
        String responseBody = objectMapper.writeValueAsString(ferienwohnung);
        sendResponse(exchange, 201, responseBody);
    }

    private Ferienwohnung parseFerienwohnungFromJson(String json) throws IOException {
        return objectMapper.readValue(json, Ferienwohnung.class);
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
