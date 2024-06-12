package de.htwg.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.htwg.backend.model.Kunde;
import de.htwg.backend.repository.KundeRepository;

@Service
public class KundeService {
    @Autowired
    private KundeRepository kundeRepository;

    public List<Kunde> getKunden() {
        return kundeRepository.findAll();
    }

}