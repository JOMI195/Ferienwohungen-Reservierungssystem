package de.htwg.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.htwg.database.DatabaseHandler;
import de.htwg.model.Bild;

public class BildRepository {

    private DatabaseHandler dbHandler;

    public BildRepository() {
        this.dbHandler = DatabaseHandler.getInstance();
    }

    public List<Bild> fetchAllBilder() {
        List<Bild> bilder = new ArrayList<>();
        String query = "SELECT * FROM bild";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Bild bild = mapResultSetToBild(rs);
                    bilder.add(bild);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bilder;
    }

    public void insertBild(Bild bild) {
        String query = "INSERT INTO bild (linkURL, ferienwohnungs_id) VALUES (?, ?)";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query)) {
                dbHandler.startTransaction();
                setBildParameters(stmt, bild);
                stmt.executeUpdate();
                dbHandler.commitTransaction();
            }
        } catch (SQLException e) {
            dbHandler.rollbackTransaction();
            e.printStackTrace();
        }
    }

    private Bild mapResultSetToBild(ResultSet rs) throws SQLException {
        return new Bild(
                rs.getString("linkURL"),
                rs.getLong("ferienwohnungs_id"));
    }

    private void setBildParameters(PreparedStatement stmt, Bild bild) throws SQLException {
        stmt.setString(1, bild.getLinkURL());
        stmt.setLong(2, bild.getFerienwohnungs_Id());
    }
}
