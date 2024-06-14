package de.htwg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public void handleGetAusstattungen(OutputStream out) throws IOException {
        List<Ausstattung> ausstattungen = ausstattungRepository.fetchAllAusstattungen();
        String responseBody = objectMapper.writeValueAsString(ausstattungen);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json; charset=UTF-8\r\n\r\n" + responseBody;
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }

    public void handlePostAusstattung(OutputStream out, String requestBody) throws IOException {
        Ausstattung ausstattung = parseAusstattungFromJson(requestBody);
        ausstattungRepository.insertAusstattung(ausstattung);
        String responseBody = objectMapper.writeValueAsString(ausstattung);
        String response = "HTTP/1.1 201 Created\r\nContent-Type: application/json; charset=UTF-8\r\n\r\n"
                + responseBody;
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }

    private Ausstattung parseAusstattungFromJson(String json) throws IOException {
        return objectMapper.readValue(json, Ausstattung.class);
    }
}
