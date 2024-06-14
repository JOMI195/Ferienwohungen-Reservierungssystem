package de.htwg.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.htwg.model.Kunde;
import de.htwg.repository.KundeRepository;

public class KundeController {

    private KundeRepository kundeRepository;
    private ObjectMapper objectMapper;

    public KundeController() {
        this.kundeRepository = new KundeRepository();
        this.objectMapper = new ObjectMapper();
    }

    public void handleGetKunden(OutputStream out) throws IOException {
        List<Kunde> kunden = kundeRepository.fetchAllKunden();
        String responseBody = objectMapper.writeValueAsString(kunden);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json; charset=UTF-8\r\n\r\n" + responseBody;
        System.out.println("All Kunde fetched");
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }

    public void handlePostKunde(OutputStream out, String requestBody) throws IOException {
        Kunde kunde = parseKundeFromJson(requestBody);
        kundeRepository.insertKunde(kunde);
        String responseBody = objectMapper.writeValueAsString(kunde);
        String response = "HTTP/1.1 201 Created\r\nContent-Type: text/plain; charset=UTF-8\r\n\r\n" + responseBody;
        System.out.println("Kunde created");
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }

    private Kunde parseKundeFromJson(String json) throws IOException {
        return objectMapper.readValue(json, Kunde.class);
    }
}
