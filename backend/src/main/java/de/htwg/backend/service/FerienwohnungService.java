package de.htwg.backend.service;

import de.htwg.backend.model.Ferienwohnung;
import de.htwg.backend.repository.FerienwohnungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class FerienwohnungService {

    @Autowired
    private FerienwohnungRepository ferienwohnungRepository;

    public List<Ferienwohnung> getAllFerienwohnungen() throws SQLException {
        return ferienwohnungRepository.getAll();
    }

    public Optional<Ferienwohnung> getFerienwohnungById(Long id) throws SQLException {
        return ferienwohnungRepository.findById(id);
    }

    public Ferienwohnung saveFerienwohnung(Ferienwohnung ferienwohnung) throws SQLException {
        return ferienwohnungRepository.save(ferienwohnung);
    }

    public void deleteFerienwohnungById(Long id) throws SQLException {
        ferienwohnungRepository.deleteById(id);
    }
}
