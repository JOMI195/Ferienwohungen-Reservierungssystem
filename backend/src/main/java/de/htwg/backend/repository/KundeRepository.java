package de.htwg.backend.repository;

import org.springframework.stereotype.Repository;

import de.htwg.backend.database.DatabaseConnection;
import de.htwg.backend.model.Kunde;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class KundeRepository {

    public List<Kunde> findAll() {
        List<Kunde> kunden = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Kunde");
            while (rs.next()) {
                Kunde kunde = new Kunde();
                kunde.setEmail(rs.getString("email"));
                kunde.setPasswort(rs.getString("passwort"));
                kunde.setVorname(rs.getString("vorname"));
                kunde.setNachname(rs.getString("nachname"));
                kunde.setIban(rs.getString("iban"));
                kunde.setNewsletter(rs.getString("newsletter").charAt(0));
                kunde.setStraße(rs.getString("straße"));
                kunde.setHausnummer(rs.getString("hausnummer"));
                kunde.setPostleitzahl(rs.getString("postleitzahl"));
                kunde.setOrt(rs.getString("ort"));
                kunde.setLandname(rs.getString("landname"));
                kunden.add(kunde);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kunden;
    }
}