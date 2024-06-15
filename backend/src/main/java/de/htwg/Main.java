package de.htwg;

import org.glassfish.grizzly.http.server.HttpServer;

import de.htwg.database.DatabaseHandler;
import de.htwg.server.Server;

public class Main {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        try {
            final HttpServer server = Server.startServer();
            DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
            System.in.read();
            server.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
