package de.htwg;

import java.io.IOException;

import de.htwg.server.HttpServer;

public class Main {
    public static void main(String[] args) {
        try {
            HttpServer server = new HttpServer(8080);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
