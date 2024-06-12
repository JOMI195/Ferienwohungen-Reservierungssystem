package de.htwg.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import de.htwg.backend.model.Kunde;

public interface KundeRepository extends JpaRepository<Kunde, String> {
}