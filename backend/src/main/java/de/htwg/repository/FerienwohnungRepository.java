package de.htwg.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.htwg.database.DatabaseHandler;
import de.htwg.model.Ferienwohnung;

public class FerienwohnungRepository {

    private DatabaseHandler dbHandler;

    public FerienwohnungRepository() {
        this.dbHandler = DatabaseHandler.getInstance();
    }

    public List<Ferienwohnung> fetchAllFerienwohnungen() {
        List<Ferienwohnung> ferienwohnungen = new ArrayList<>();
        String query = """
                SELECT f.*, avg_b.avgSterne
                FROM Ferienwohnung f
                LEFT JOIN (
                    SELECT b.ferienwohnungs_id, AVG(b.sterne) AS avgSterne
                    FROM Buchung b
                    GROUP BY b.ferienwohnungs_id
                ) avg_b ON f.ferienwohnungs_id = avg_b.ferienwohnungs_id
                ORDER BY f.ferienwohnungs_id ASC
                """;
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

    public List<Map<String, Long>> fetchFilteredFerienwohnungenIds(
            String landname,
            String ausstattung,
            String startdatum,
            String enddatum) {
        List<Map<String, Long>> ferienwohnungIds = new ArrayList<>();
        StringBuilder query = new StringBuilder("""
                    SELECT f.Ferienwohnungs_ID
                    FROM Ferienwohnung f
                    LEFT JOIN dbsys36.besitzt bs ON f.Ferienwohnungs_ID = bs.Ferienwohnungs_ID
                    LEFT JOIN dbsys36.Buchung b ON f.Ferienwohnungs_ID = b.Ferienwohnungs_ID
                    WHERE f.ferienwohnungs_id IN (SELECT ferienwohnungs_id FROM buchung)
                """);

        if (startdatum != null && !startdatum.isEmpty() && enddatum != null && !enddatum.isEmpty()) {
            query.append("""
                        AND f.Ferienwohnungs_ID NOT IN (
                            SELECT Ferienwohnungs_ID
                            FROM dbsys36.Buchung
                            WHERE
                                (Startdatum BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') OR
                                Enddatum BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') OR
                                (Startdatum < TO_DATE(?, 'YYYY-MM-DD') AND
                                Enddatum > TO_DATE(?, 'YYYY-MM-DD')))
                        )
                    """);
        }

        if (ausstattung != null && !ausstattung.isEmpty()) {
            query.append(" AND bs.Ausstattungsname = ?");
        }

        if (landname != null && !landname.isEmpty()) {
            query.append(" AND f.Landname = ?");
        }

        query.append(" GROUP BY f.Ferienwohnungs_ID");

        try {
            dbHandler.checkConnection();
            try (PreparedStatement stmt = dbHandler.getConnection().prepareStatement(query.toString())) {
                int index = 1;

                if (startdatum != null && !startdatum.isEmpty() && enddatum != null && !enddatum.isEmpty()) {
                    stmt.setString(index++, startdatum);
                    stmt.setString(index++, enddatum);
                    stmt.setString(index++, startdatum);
                    stmt.setString(index++, enddatum);
                    stmt.setString(index++, startdatum);
                    stmt.setString(index++, enddatum);
                }

                if (ausstattung != null && !ausstattung.isEmpty()) {
                    stmt.setString(index++, ausstattung);
                }

                if (landname != null && !landname.isEmpty()) {
                    stmt.setString(index++, landname);
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Long> map = new HashMap<>();
                        map.put("Ferienwohnungs_ID", rs.getLong("Ferienwohnungs_ID"));
                        ferienwohnungIds.add(map);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ferienwohnungIds;
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
        double avgSterne = rs.getDouble("avgSterne");
        Double avgSterneWrapper = rs.wasNull() ? null : avgSterne;

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
                rs.getString("landname"),
                avgSterneWrapper);
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
