package de.htwg.repository;

import de.htwg.database.DatabaseHandler;
import de.htwg.model.Besitzt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BesitztRepository {

    private final DatabaseHandler dbHandler;

    public BesitztRepository() {
        this.dbHandler = DatabaseHandler.getInstance();
    }

    public List<Besitzt> fetchAllBesitzt() {
        List<Besitzt> besitztList = new ArrayList<>();
        String query = "SELECT * FROM besitzt ORDER BY ferienwohnungs_id ASC";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Besitzt besitzt = mapResultSetToBesitzt(rs);
                    besitztList.add(besitzt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return besitztList;
    }

    public void insertBesitzt(Besitzt besitzt) {
        String query = "INSERT INTO besitzt (ferienwohnungs_id, ausstattungsname) VALUES (?, ?)";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query)) {
                dbHandler.startTransaction();
                setBesitztParameters(stmt, besitzt);
                stmt.executeUpdate();
                dbHandler.commitTransaction();
            }
        } catch (SQLException e) {
            dbHandler.rollbackTransaction();
            e.printStackTrace();
        }
    }

    private Besitzt mapResultSetToBesitzt(ResultSet rs) throws SQLException {
        return new Besitzt(
                rs.getLong("ferienwohnungs_id"),
                rs.getString("ausstattungsname"));
    }

    private void setBesitztParameters(PreparedStatement stmt, Besitzt besitzt) throws SQLException {
        stmt.setLong(1, besitzt.getFerienwohnungs_Id());
        stmt.setString(2, besitzt.getAusstattungsname());
    }
}
