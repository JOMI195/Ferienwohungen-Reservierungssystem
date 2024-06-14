package de.htwg.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.htwg.model.Ferienwohnung;
import de.htwg.repository.FerienwohnungRepository;

public class FerienwohnungController {

    private FerienwohnungRepository ferienwohnungRepository;
    private ObjectMapper objectMapper;

    public FerienwohnungController() {
        this.ferienwohnungRepository = new FerienwohnungRepository();
        this.objectMapper = new ObjectMapper();
    }

    public void handleGetFerienwohnungen(OutputStream out) throws IOException {
        List<Ferienwohnung> ferienwohnungen = ferienwohnungRepository.fetchAllFerienwohnungen();
        String responseBody = objectMapper.writeValueAsString(ferienwohnungen);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json; charset=UTF-8\r\n\r\n" + responseBody;
        System.out.println("All Ferienwohnungen fetched");
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }

    public void handlePostFerienwohnung(OutputStream out, String requestBody) throws IOException {
        Ferienwohnung ferienwohnung = parseFerienwohnungFromJson(requestBody);
        ferienwohnungRepository.insertFerienwohnung(ferienwohnung);
        String responseBody = objectMapper.writeValueAsString(ferienwohnung);
        String response = "HTTP/1.1 201 Created\r\nContent-Type: application/json; charset=UTF-8\r\n\r\n" + responseBody;
        System.out.println("Ferienwohnung created");
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }

    private Ferienwohnung parseFerienwohnungFromJson(String json) throws IOException {
        return objectMapper.readValue(json, Ferienwohnung.class);
    }
}
