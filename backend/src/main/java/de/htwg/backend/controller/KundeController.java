package de.htwg.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.htwg.backend.model.Kunde;
import de.htwg.backend.service.KundeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class KundeController {

    @Autowired
    private KundeService service;

    @GetMapping("/kunde")
    public List<Kunde> getKunden() {
        return service.getKunden();
    }
}
