package de.htwg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public void handleGetBesitzt(OutputStream out) throws IOException {
        List<Besitzt> besitztList = besitztRepository.fetchAllBesitzt();
        String responseBody = objectMapper.writeValueAsString(besitztList);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json; charset=UTF-8\r\n\r\n" + responseBody;
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }

    public void handlePostBesitzt(OutputStream out, String requestBody) throws IOException {
        Besitzt besitzt = parseBesitztFromJson(requestBody);
        besitztRepository.insertBesitzt(besitzt);
        String responseBody = objectMapper.writeValueAsString(besitzt);
        String response = "HTTP/1.1 201 Created\r\nContent-Type: application/json; charset=UTF-8\r\n\r\n"
                + responseBody;
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }

    private Besitzt parseBesitztFromJson(String json) throws IOException {
        return objectMapper.readValue(json, Besitzt.class);
    }
}
