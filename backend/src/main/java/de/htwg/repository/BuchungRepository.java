package de.htwg.repository;

import de.htwg.database.DatabaseHandler;
import de.htwg.model.Buchung;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class BuchungRepository {

    private final DatabaseHandler dbHandler;

    public BuchungRepository() {
        this.dbHandler = DatabaseHandler.getInstance();
    }

    public List<Buchung> fetchAllBuchungen() {
        List<Buchung> buchungen = new ArrayList<>();
        String query = "SELECT * FROM buchung ORDER BY buchnungsnummer ASC";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Buchung buchung = mapResultSetToBuchung(rs);
                    buchungen.add(buchung);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buchungen;
    }

    public void insertBuchung(Buchung buchung) {
        String query = "INSERT INTO buchung (ferienwohnungs_id, email, buchungsdatum, startdatum, enddatum, sterne, bewertungsdatum, rechnungsnummer, rechnungsbetrag, rechnungsdatum) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query)) {
                dbHandler.startTransaction();
                setBuchungParameters(stmt, buchung);
                stmt.executeUpdate();
                dbHandler.commitTransaction();
            }
        } catch (SQLException e) {
            dbHandler.rollbackTransaction();
            e.printStackTrace();
        }
    }

    private Buchung mapResultSetToBuchung(ResultSet rs) throws SQLException {
        return new Buchung(
                rs.getLong("buchnungsnummer"),
                rs.getLong("ferienwohnungs_id"),
                rs.getString("email"),
                rs.getDate("buchungsdatum").toLocalDate(),
                rs.getDate("startdatum").toLocalDate(),
                rs.getDate("enddatum").toLocalDate(),
                rs.getInt("sterne"),
                rs.getDate("bewertungsdatum") != null ? rs.getDate("bewertungsdatum").toLocalDate() : null,
                rs.getInt("rechnungsnummer"),
                rs.getDouble("rechnungsbetrag"),
                rs.getDate("rechnungsdatum") != null ? rs.getDate("rechnungsdatum").toLocalDate() : null);
    }

    private void setBuchungParameters(PreparedStatement stmt, Buchung buchung) throws SQLException {
        stmt.setLong(1, buchung.getFerienwohnungs_Id());
        stmt.setString(2, buchung.getEmail());
        stmt.setDate(3, buchung.getBuchungsdatum() != null ? Date.valueOf(buchung.getBuchungsdatum()) : null);
        stmt.setDate(4, buchung.getStartdatum() != null ? Date.valueOf(buchung.getStartdatum()) : null);
        stmt.setDate(5, buchung.getEnddatum() != null ? Date.valueOf(buchung.getEnddatum()) : null);
        if (buchung.getSterne() != null) {
            stmt.setInt(6, buchung.getSterne());
        } else {
            stmt.setNull(6, Types.INTEGER);
        }
        stmt.setDate(7, buchung.getBewertungsdatum() != null ? Date.valueOf(buchung.getBewertungsdatum()) : null);
        stmt.setInt(8, buchung.getRechnungsnummer());
        stmt.setDouble(9, buchung.getRechnungsbetrag());
        stmt.setDate(10, buchung.getRechnungsdatum() != null ? Date.valueOf(buchung.getRechnungsdatum()) : null);
    }
}
