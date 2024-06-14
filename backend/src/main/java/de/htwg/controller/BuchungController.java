package de.htwg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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

    public void handleGetBuchungen(OutputStream out) throws IOException {
        List<Buchung> buchungen = buchungRepository.fetchAllBuchungen();
        String responseBody = objectMapper.writeValueAsString(buchungen);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json; charset=UTF-8\r\n\r\n" + responseBody;
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }

    public void handlePostBuchung(OutputStream out, String requestBody) throws IOException {
        Buchung buchung = parseBuchungFromJson(requestBody);
        buchungRepository.insertBuchung(buchung);
        String responseBody = objectMapper.writeValueAsString(buchung);
        String response = "HTTP/1.1 201 Created\r\nContent-Type: application/json; charset=UTF-8\r\n\r\n"
                + responseBody;
        System.out.println("Buchung created");
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }

    private Buchung parseBuchungFromJson(String json) throws IOException {
        return objectMapper.readValue(json, Buchung.class);
    }
}
