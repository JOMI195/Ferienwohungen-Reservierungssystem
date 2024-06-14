package de.htwg.backend.controller;

import de.htwg.backend.model.Kunde;
import de.htwg.backend.service.KundeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/kunde")
public class KundeController {

    @Autowired
    private KundeService kundeService;

    @GetMapping
    public ResponseEntity<List<Kunde>> getAllKunden() {
        try {
            List<Kunde> kunden = kundeService.getAllKunden();
            return ResponseEntity.ok(kunden);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<Kunde> getKundeByEmail(@PathVariable String email) {
        try {
            Optional<Kunde> kunde = kundeService.getKundeByEmail(email);
            return kunde.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Kunde> createKunde(@RequestBody Kunde kunde) {
        try {
            Kunde createdKunde = kundeService.saveKunde(kunde);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdKunde);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteKundeByEmail(@PathVariable String email) {
        try {
            kundeService.deleteKundeByEmail(email);
            return ResponseEntity.noContent().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
