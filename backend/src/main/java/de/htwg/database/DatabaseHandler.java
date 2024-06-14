package de.htwg.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseHandler {
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("./")
            .load();
    private static final String DB_URL = dotenv.get("DB_URL");
    private static final String DB_USER = dotenv.get("DB_USERNAME");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    private static DatabaseHandler instance;
    private Connection connection;

    private DatabaseHandler() {
        try {
            checkConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

    public void checkConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database: " + DB_URL);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void rollbackTransaction() {
        try {
            if (connection != null) {
                connection.rollback();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
