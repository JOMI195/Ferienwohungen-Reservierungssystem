package de.htwg.backend.repository;

import de.htwg.backend.model.Ferienwohnung;
import de.htwg.backend.database.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FerienwohnungRepository {

    @Autowired
    private DatabaseConnection databaseConnection;

    public List<Ferienwohnung> getAll() throws SQLException {
        List<Ferienwohnung> ferienwohnungen = new ArrayList<>();
        String query = "SELECT * FROM Ferienwohnung";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            databaseConnection.beginTransaction();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ferienwohnung ferienwohnung = mapResultSetToEntity(rs);
                    ferienwohnungen.add(ferienwohnung);
                }
            }
        } catch (SQLException e) {
            databaseConnection.rollback();
            e.printStackTrace();
            throw e;
        }
        return ferienwohnungen;
    }

    public Optional<Ferienwohnung> findById(Long id) throws SQLException {
        String query = "SELECT * FROM Ferienwohnung WHERE Ferienwohnungs_ID = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            databaseConnection.beginTransaction();
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Ferienwohnung ferienwohnung = mapResultSetToEntity(rs);
                    return Optional.of(ferienwohnung);
                }
            }
        } catch (SQLException e) {
            databaseConnection.rollback();
            e.printStackTrace();
            throw e;
        }
        return Optional.empty();
    }

    public Ferienwohnung save(Ferienwohnung ferienwohnung) throws SQLException {
        String insertQuery = "INSERT INTO ferienwohnung (ferienwohnungsname, mietpreis, zimmer, größe, " +
                "straße, hausnummer, postleitzahl, ort, landname) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            databaseConnection.beginTransaction();
            stmt.setString(1, ferienwohnung.getFerienwohnungsname());
            stmt.setDouble(2, ferienwohnung.getMietpreis());
            stmt.setInt(3, ferienwohnung.getZimmer());
            stmt.setInt(4, ferienwohnung.getGröße());
            stmt.setString(5, ferienwohnung.getStraße());
            stmt.setString(6, ferienwohnung.getHausnummer());
            stmt.setString(7, ferienwohnung.getPostleitzahl());
            stmt.setString(8, ferienwohnung.getOrt());
            stmt.setString(9, ferienwohnung.getLandname());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating Ferienwohnung failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ferienwohnung.setFerienwohnungsId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating Ferienwohnung failed, no ID obtained.");
                }
            }
            databaseConnection.commit();
        } catch (SQLException e) {
            databaseConnection.rollback();
            e.printStackTrace();
            throw e;
        }
        return ferienwohnung;
    }

    public void deleteById(Long id) throws SQLException {
        String deleteQuery = "DELETE FROM Ferienwohnung WHERE Ferienwohnungs_ID = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            databaseConnection.beginTransaction();
            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting Ferienwohnung failed, no rows affected.");
            }
            databaseConnection.commit();
        } catch (SQLException e) {
            databaseConnection.rollback();
            e.printStackTrace();
            throw e;
        }
    }

    private Ferienwohnung mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Ferienwohnung(
                rs.getLong("Ferienwohnungs_ID"),
                rs.getString("Ferienwohnungsname"),
                rs.getDouble("Mietpreis"),
                rs.getInt("Zimmer"),
                rs.getInt("Größe"),
                rs.getString("Straße"),
                rs.getString("Hausnummer"),
                rs.getString("Postleitzahl"),
                rs.getString("Ort"),
                rs.getString("Landname")
        );
    }
}
