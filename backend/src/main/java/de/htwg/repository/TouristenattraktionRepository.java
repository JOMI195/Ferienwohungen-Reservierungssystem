package de.htwg.repository;

import de.htwg.database.DatabaseHandler;
import de.htwg.model.Touristenattraktion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TouristenattraktionRepository {

    private final DatabaseHandler dbHandler;

    public TouristenattraktionRepository() {
        this.dbHandler = DatabaseHandler.getInstance();
    }

    public List<Touristenattraktion> fetchAllTouristenattraktionen() {
        List<Touristenattraktion> touristenattraktionen = new ArrayList<>();
        String query = "SELECT * FROM Touristenattraktion";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Touristenattraktion touristenattraktion = mapResultSetToTouristenattraktion(rs);
                    touristenattraktionen.add(touristenattraktion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return touristenattraktionen;
    }

    public void insertTouristenattraktion(Touristenattraktion touristenattraktion) {
        String query = "INSERT INTO Touristenattraktion (Touristenattraktionsname, Beschreibung) VALUES (?, ?)";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query)) {
                dbHandler.startTransaction();
                setTouristenattraktionParameters(stmt, touristenattraktion);
                stmt.executeUpdate();
                dbHandler.commitTransaction();
            }
        } catch (SQLException e) {
            dbHandler.rollbackTransaction();
            e.printStackTrace();
        }
    }

    private Touristenattraktion mapResultSetToTouristenattraktion(ResultSet rs) throws SQLException {
        return new Touristenattraktion(
                rs.getString("Touristenattraktionsname"),
                rs.getString("Beschreibung"));
    }

    private void setTouristenattraktionParameters(PreparedStatement stmt, Touristenattraktion touristenattraktion)
            throws SQLException {
        stmt.setString(1, touristenattraktion.getTouristenattraktionsname());
        stmt.setString(2, touristenattraktion.getBeschreibung());
    }
}
