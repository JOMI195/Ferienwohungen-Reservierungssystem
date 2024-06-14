package de.htwg.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.htwg.backend.model.Kunde;
import de.htwg.backend.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class KundeRepository {

    @Autowired
    private DatabaseConnection databaseConnection;

    public List<Kunde> getAll() throws SQLException {
        List<Kunde> kunden = new ArrayList<>();
        String query = "SELECT * FROM kunde";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            databaseConnection.beginTransaction();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Kunde kunde = mapResultSetToEntity(rs);
                    kunden.add(kunde);
                }
            }
        } catch (SQLException e) {
            databaseConnection.rollback();
            e.printStackTrace();
            throw e;
        }
        return kunden;
    }

    public Optional<Kunde> findById(String email) throws SQLException {
        String query = "SELECT * FROM kunde WHERE email = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            databaseConnection.beginTransaction();
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Kunde kunde = mapResultSetToEntity(rs);
                    return Optional.of(kunde);
                }
            }
        } catch (SQLException e) {
            databaseConnection.rollback();
            e.printStackTrace();
            throw e;
        }
        return Optional.empty();
    }

    public Kunde save(Kunde kunde) throws SQLException {
        String insertQuery = "INSERT INTO kunde (email, passwort, vorname, nachname, iban, newsletter, " +
                "straße, hausnummer, postleitzahl, ort, landname) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            databaseConnection.beginTransaction();
            stmt.setString(1, kunde.getEmail());
            stmt.setString(2, kunde.getPasswort());
            stmt.setString(3, kunde.getVorname());
            stmt.setString(4, kunde.getNachname());
            stmt.setString(5, kunde.getIban());
            stmt.setString(6, String.valueOf(kunde.getNewsletter()));
            stmt.setString(7, kunde.getStraße());
            stmt.setString(8, kunde.getHausnummer());
            stmt.setString(9, kunde.getPostleitzahl());
            stmt.setString(10, kunde.getOrt());
            stmt.setString(11, kunde.getLandname());
    
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating Kunde failed, no rows affected.");
            }
    
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Assuming email is auto-generated as a primary key
                    kunde.setEmail(generatedKeys.getString(1)); // Or adjust accordingly based on generated key
                } else {
                    throw new SQLException("Creating Kunde failed, no ID obtained.");
                }
            }
    
            databaseConnection.commit();
        } catch (SQLException e) {
            databaseConnection.rollback();
            e.printStackTrace();
            throw e;
        }
        return kunde;
    }
    

    public void deleteByEmail(String email) throws SQLException {
        String deleteQuery = "DELETE FROM kunde WHERE email = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            databaseConnection.beginTransaction();
            stmt.setString(1, email);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting Kunde failed, no rows affected.");
            }
            databaseConnection.commit();
        } catch (SQLException e) {
            databaseConnection.rollback();
            e.printStackTrace();
            throw e;
        }
    }

    private Kunde mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Kunde(
                rs.getString("email"),
                rs.getString("passwort"),
                rs.getString("vorname"),
                rs.getString("nachname"),
                rs.getString("iban"),
                rs.getString("newsletter").charAt(0),
                rs.getString("straße"),
                rs.getString("hausnummer"),
                rs.getString("postleitzahl"),
                rs.getString("ort"),
                rs.getString("landname")
        );
    }
}
