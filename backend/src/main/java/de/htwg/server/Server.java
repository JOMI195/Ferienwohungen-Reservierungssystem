package de.htwg.server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import de.htwg.env.DotenvSingleton;
import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;

public class Server {
    private static Dotenv dotenv = DotenvSingleton.getInstance();
    private static final String BASE_URI = dotenv.get("API_URL");

    public static HttpServer startServer() {
        final ResourceConfig rc = new JerseyConfig();
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
        System.out.println(String.format("Server started at %s", BASE_URI));
        return server;
    }
}
