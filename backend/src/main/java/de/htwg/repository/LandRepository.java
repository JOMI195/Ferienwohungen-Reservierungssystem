package de.htwg.repository;

import de.htwg.database.DatabaseHandler;
import de.htwg.model.Land;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LandRepository {

    private final DatabaseHandler dbHandler;

    public LandRepository() {
        this.dbHandler = DatabaseHandler.getInstance();
    }

    public List<Land> fetchAllLaender() {
        List<Land> laender = new ArrayList<>();
        String query = "SELECT * FROM land";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Land land = mapResultSetToLand(rs);
                    laender.add(land);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laender;
    }

    public void insertLand(Land land) {
        String query = "INSERT INTO land (landname) VALUES (?)";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query)) {
                dbHandler.startTransaction();
                setLandParameters(stmt, land);
                stmt.executeUpdate();
                dbHandler.commitTransaction();
            }
        } catch (SQLException e) {
            dbHandler.rollbackTransaction();
            e.printStackTrace();
        }
    }

    private Land mapResultSetToLand(ResultSet rs) throws SQLException {
        return new Land(
                rs.getString("landname"));
    }

    private void setLandParameters(PreparedStatement stmt, Land land) throws SQLException {
        stmt.setString(1, land.getLandname());
    }
}
