package de.htwg.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseHandler {
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("./")
            .load();
    private static final String DB_URL = dotenv.get("DB_URL");
    private static final String DB_USER = dotenv.get("DB_USERNAME");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    private static final int MAX_RECONNECT_ATTEMPTS = 10;
    private static final long RECONNECT_INTERVAL_MS = 5000;

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
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Connected to database: " + DB_URL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            tryReconnect();
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                tryReconnect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private void tryReconnect() {
        int attempt = 0;
        while (attempt < MAX_RECONNECT_ATTEMPTS) {
            try {
                System.out.println("Attempting to reconnect to database... Attempt " + (attempt + 1));
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Reconnected to database: " + DB_URL);
                break;
            } catch (SQLException e) {
                e.printStackTrace();
                attempt++;
                try {
                    TimeUnit.MILLISECONDS.sleep(RECONNECT_INTERVAL_MS);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (attempt == MAX_RECONNECT_ATTEMPTS) {
            System.err.println("Failed to reconnect after " + attempt + " attempts. Exiting...");
            System.exit(1);
        }
    }
}
