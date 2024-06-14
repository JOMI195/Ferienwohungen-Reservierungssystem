package de.htwg.repository;

import de.htwg.database.DatabaseHandler;
import de.htwg.model.Ausstattung;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AusstattungRepository {

    private final DatabaseHandler dbHandler;

    public AusstattungRepository() {
        this.dbHandler = DatabaseHandler.getInstance();
    }

    public List<Ausstattung> fetchAllAusstattungen() {
        List<Ausstattung> ausstattungen = new ArrayList<>();
        String query = "SELECT * FROM ausstattung";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Ausstattung ausstattung = mapResultSetToAusstattung(rs);
                    ausstattungen.add(ausstattung);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ausstattungen;
    }

    public void insertAusstattung(Ausstattung ausstattung) {
        String query = "INSERT INTO ausstattung (ausstattungsname) VALUES (?)";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query)) {
                dbHandler.startTransaction();
                setAusstattungParameters(stmt, ausstattung);
                stmt.executeUpdate();
                dbHandler.commitTransaction();
            }
        } catch (SQLException e) {
            dbHandler.rollbackTransaction();
            e.printStackTrace();
        }
    }

    private Ausstattung mapResultSetToAusstattung(ResultSet rs) throws SQLException {
        return new Ausstattung(
                rs.getString("ausstattungsname"));
    }

    private void setAusstattungParameters(PreparedStatement stmt, Ausstattung ausstattung) throws SQLException {
        stmt.setString(1, ausstattung.getAusstattungsname());
    }
}
