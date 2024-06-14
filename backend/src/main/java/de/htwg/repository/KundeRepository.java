package de.htwg.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.htwg.database.DatabaseHandler;
import de.htwg.model.Kunde;

public class KundeRepository {

    private DatabaseHandler dbHandler;

    public KundeRepository() {
        this.dbHandler = DatabaseHandler.getInstance();
    }

    public List<Kunde> fetchAllKunden() {
        List<Kunde> kunden = new ArrayList<>();
        String query = "SELECT * FROM kunde";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Kunde kunde = mapResultSetToKunde(rs);
                    kunden.add(kunde);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kunden;
    }

    public void insertKunde(Kunde kunde) {
        String query = "INSERT INTO kunde (email, passwort, vorname, nachname, iban, newsletter, straße, hausnummer, postleitzahl, ort, landname) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query)) {
                dbHandler.startTransaction();
                setKundeParameters(stmt, kunde);
                stmt.executeUpdate();
                dbHandler.commitTransaction();
            }
        } catch (SQLException e) {
            dbHandler.rollbackTransaction();
            e.printStackTrace();
        }
    }

    private Kunde mapResultSetToKunde(ResultSet rs) throws SQLException {
        return new Kunde(
                rs.getString("email"),
                rs.getString("passwort"),
                rs.getString("vorname"),
                rs.getString("nachname"),
                rs.getString("iban"),
                rs.getString("newsletter").charAt(0),
                rs.getString("straße"),
                rs.getString("hausnummer"),
                rs.getString("postleitzahl"),
                rs.getString("ort"),
                rs.getString("landname"));
    }

    private void setKundeParameters(PreparedStatement stmt, Kunde kunde) throws SQLException {
        stmt.setString(1, kunde.getEmail());
        stmt.setString(2, kunde.getPasswort());
        stmt.setString(3, kunde.getVorname());
        stmt.setString(4, kunde.getNachname());
        stmt.setString(5, kunde.getIban());
        stmt.setString(6, String.valueOf(kunde.getNewsletter()));
        stmt.setString(7, kunde.getStraße());
        stmt.setString(8, kunde.getHausnummer());
        stmt.setString(9, kunde.getPostleitzahl());
        stmt.setString(10, kunde.getOrt());
        stmt.setString(11, kunde.getLandname());
    }
}
