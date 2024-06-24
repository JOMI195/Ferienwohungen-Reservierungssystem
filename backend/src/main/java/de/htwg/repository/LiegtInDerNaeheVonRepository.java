package de.htwg.repository;

import de.htwg.database.DatabaseHandler;
import de.htwg.model.LiegtInDerNaeheVon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LiegtInDerNaeheVonRepository {

    private final DatabaseHandler dbHandler;

    public LiegtInDerNaeheVonRepository() {
        this.dbHandler = DatabaseHandler.getInstance();
    }

    public List<LiegtInDerNaeheVon> fetchAll() {
        List<LiegtInDerNaeheVon> list = new ArrayList<>();
        String query = "SELECT * FROM Liegt_In_Der_Naehe_Von";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    LiegtInDerNaeheVon item = mapResultSetToLiegtInDerNaeheVon(rs);
                    list.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(LiegtInDerNaeheVon item) {
        String query = "INSERT INTO Liegt_In_Der_Naehe_Von (Ferienwohnungs_ID, Touristenattraktionsname, Entfernung) VALUES (?, ?, ?)";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query)) {
                dbHandler.startTransaction();
                setParameters(stmt, item);
                stmt.executeUpdate();
                dbHandler.commitTransaction();
            }
        } catch (SQLException e) {
            dbHandler.rollbackTransaction();
            e.printStackTrace();
        }
    }

    private LiegtInDerNaeheVon mapResultSetToLiegtInDerNaeheVon(ResultSet rs) throws SQLException {
        return new LiegtInDerNaeheVon(
                rs.getLong("Ferienwohnungs_ID"),
                rs.getString("Touristenattraktionsname"),
                rs.getInt("Entfernung"));
    }

    private void setParameters(PreparedStatement stmt, LiegtInDerNaeheVon item) throws SQLException {
        stmt.setLong(1, item.getFerienwohnungs_id());
        stmt.setString(2, item.getTouristenattraktionsname());
        stmt.setInt(3, item.getEntfernung());
    }
}
