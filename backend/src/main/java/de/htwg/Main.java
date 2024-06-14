package de.htwg;

import java.io.IOException;

import de.htwg.server.Server;

public class Main {
    public static void main(String[] args) {
        try {
            Server server = new Server(8080);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
