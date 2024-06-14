package de.htwg.backend.service;

import de.htwg.backend.model.Kunde;
import de.htwg.backend.repository.KundeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class KundeService {

    @Autowired
    private KundeRepository kundeRepository;

    public List<Kunde> getAllKunden() throws SQLException {
        return kundeRepository.getAll();
    }

    public Optional<Kunde> getKundeByEmail(String email) throws SQLException {
        return kundeRepository.findById(email);
    }

    public Kunde saveKunde(Kunde kunde) throws SQLException {
        return kundeRepository.save(kunde);
    }

    public void deleteKundeByEmail(String email) throws SQLException {
        kundeRepository.deleteByEmail(email);
    }
}
