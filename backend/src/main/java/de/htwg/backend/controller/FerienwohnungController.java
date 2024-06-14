package de.htwg.backend.controller;

import de.htwg.backend.model.Ferienwohnung;
import de.htwg.backend.service.FerienwohnungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ferienwohnung")
public class FerienwohnungController {

    @Autowired
    private FerienwohnungService ferienwohnungService;

    @GetMapping
    public ResponseEntity<List<Ferienwohnung>> getAllFerienwohnungen() {
        try {
            List<Ferienwohnung> ferienwohnungen = ferienwohnungService.getAllFerienwohnungen();
            return ResponseEntity.ok(ferienwohnungen);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ferienwohnung> getFerienwohnungById(@PathVariable Long id) {
        try {
            Optional<Ferienwohnung> ferienwohnung = ferienwohnungService.getFerienwohnungById(id);
            return ferienwohnung.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createFerienwohnung(@RequestBody Ferienwohnung ferienwohnung) {
        try {
            Ferienwohnung createdFerienwohnung = ferienwohnungService.saveFerienwohnung(ferienwohnung);
            return ResponseEntity.ok(createdFerienwohnung);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Ferienwohnung: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFerienwohnungById(@PathVariable Long id) {
        try {
            ferienwohnungService.deleteFerienwohnungById(id);
            return ResponseEntity.noContent().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
