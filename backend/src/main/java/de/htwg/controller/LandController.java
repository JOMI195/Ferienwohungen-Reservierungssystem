package de.htwg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public void handleGetLaender(OutputStream out) throws IOException {
        List<Land> laender = landRepository.fetchAllLaender();
        String responseBody = objectMapper.writeValueAsString(laender);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json; charset=UTF-8\r\n\r\n" + responseBody;
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }

    public void handlePostLand(OutputStream out, String requestBody) throws IOException {
        Land land = parseLandFromJson(requestBody);
        landRepository.insertLand(land);
        String responseBody = objectMapper.writeValueAsString(land);
        String response = "HTTP/1.1 201 Created\r\nContent-Type: application/json; charset=UTF-8\r\n\r\n"
                + responseBody;
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }

    private Land parseLandFromJson(String json) throws IOException {
        return objectMapper.readValue(json, Land.class);
    }
}
