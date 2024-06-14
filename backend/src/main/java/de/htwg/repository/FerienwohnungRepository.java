package de.htwg.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.htwg.database.DatabaseHandler;
import de.htwg.model.Ferienwohnung;

public class FerienwohnungRepository {

    private DatabaseHandler dbHandler;

    public FerienwohnungRepository() {
        this.dbHandler = DatabaseHandler.getInstance();
    }

    public List<Ferienwohnung> fetchAllFerienwohnungen() {
        List<Ferienwohnung> ferienwohnungen = new ArrayList<>();
        String query = "SELECT * FROM ferienwohnung ORDER BY ferienwohnungs_id ASC";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Ferienwohnung ferienwohnung = mapResultSetToFerienwohnung(rs);
                    ferienwohnungen.add(ferienwohnung);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ferienwohnungen;
    }

    public void insertFerienwohnung(Ferienwohnung ferienwohnung) {
        String query = "INSERT INTO ferienwohnung (ferienwohnungsname, mietpreis, zimmer, größe, straße, hausnummer, postleitzahl, ort, landname) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query)) {
                dbHandler.startTransaction();
                setFerienwohnungParameters(stmt, ferienwohnung);
                stmt.executeUpdate();
                dbHandler.commitTransaction();
            }
        } catch (SQLException e) {
            dbHandler.rollbackTransaction();
            e.printStackTrace();
        }
    }

    private Ferienwohnung mapResultSetToFerienwohnung(ResultSet rs) throws SQLException {
        return new Ferienwohnung(
                rs.getLong("ferienwohnungs_id"),
                rs.getString("ferienwohnungsname"),
                rs.getDouble("mietpreis"),
                rs.getInt("zimmer"),
                rs.getInt("größe"),
                rs.getString("straße"),
                rs.getString("hausnummer"),
                rs.getString("postleitzahl"),
                rs.getString("ort"),
                rs.getString("landname"));
    }

    private void setFerienwohnungParameters(PreparedStatement stmt, Ferienwohnung ferienwohnung) throws SQLException {
        stmt.setString(1, ferienwohnung.getFerienwohnungsname());
        stmt.setDouble(2, ferienwohnung.getMietpreis());
        stmt.setInt(3, ferienwohnung.getZimmer());
        stmt.setInt(4, ferienwohnung.getGröße());
        stmt.setString(5, ferienwohnung.getStraße());
        stmt.setString(6, ferienwohnung.getHausnummer());
        stmt.setString(7, ferienwohnung.getPostleitzahl());
        stmt.setString(8, ferienwohnung.getOrt());
        stmt.setString(9, ferienwohnung.getLandname());
    }
}
