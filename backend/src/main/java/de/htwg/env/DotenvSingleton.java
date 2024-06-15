package de.htwg.env;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvSingleton {

    private static Dotenv instance;

    private DotenvSingleton() {
    }

    public static Dotenv getInstance() {
        if (instance == null) {
            synchronized (DotenvSingleton.class) {
                if (instance == null) {
                    instance = Dotenv.configure()
                            .directory("./")
                            .load();
                }
            }
        }
        return instance;
    }
}
