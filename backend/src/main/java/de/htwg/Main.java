package de.htwg;

import org.glassfish.grizzly.http.server.HttpServer;

import de.htwg.database.DatabaseHandler;
import de.htwg.server.Server;

public class Main {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        try {
            final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
            final HttpServer server = Server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
