package de.htwg.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.htwg.controller.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Server {
    private int port;

    private static final String KUNDE_URL = "/api/kunde";
    private static final String FERIENWOHNUNG_URL = "/api/ferienwohnung";
    private static final String BESITZT_URL = "/api/besitzt";
    private static final String AUSSTATTUNG_URL = "/api/ausstattung";
    private static final String LAND_URL = "/api/land";
    private static final String BUCHUNG_URL = "/api/buchung";

    private KundeController kundeController;
    private FerienwohnungController ferienwohnungController;
    private BesitztController besitztController;
    private AusstattungController ausstattungController;
    private LandController landController;
    private BuchungController buchungController;

    public Server(int port) {
        this.port = port;

        this.kundeController = new KundeController();
        this.ferienwohnungController = new FerienwohnungController();
        this.besitztController = new BesitztController();
        this.ausstattungController = new AusstattungController();
        this.landController = new LandController();
        this.buchungController = new BuchungController();
    }

    public void start() throws IOException {
        System.out.println("Starting server ...");

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new RequestHandler());
        server.setExecutor(null); // creates a default executor
        server.start();

        System.out.println("Server started on port " + port);
    }

    private class RequestHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestMethod = exchange.getRequestMethod();

            // Handle CORS preflight requests
            if (requestMethod.equalsIgnoreCase("OPTIONS")) {
                handleCorsPreflight(exchange);
                return;
            }

            // Add CORS headers to actual requests
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            // Process actual requests
            handleRequest(exchange);
        }

        private void handleCorsPreflight(HttpExchange exchange) throws IOException {
            Headers headers = exchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization");

            exchange.sendResponseHeaders(204, -1);
            exchange.close();
        }

        private void handleRequest(HttpExchange exchange) throws IOException {
            String requestMethod = exchange.getRequestMethod();
            String requestPath = exchange.getRequestURI().getPath();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
            String requestBody = reader.lines().collect(Collectors.joining("\n"));
            reader.close();

            try (OutputStream responseBody = exchange.getResponseBody()) {
                switch (requestPath) {
                    case KUNDE_URL:
                        if ("GET".equals(requestMethod)) {
                            kundeController.handleGetKunden(exchange);
                        } else if ("POST".equals(requestMethod)) {
                            kundeController.handlePostKunde(exchange, requestBody);
                        }
                        break;
                    case FERIENWOHNUNG_URL:
                        if ("GET".equals(requestMethod)) {
                            ferienwohnungController.handleGetFerienwohnungen(exchange);
                        } else if ("POST".equals(requestMethod)) {
                            ferienwohnungController.handlePostFerienwohnung(exchange, requestBody);
                        }
                        break;
                    case BESITZT_URL:
                        if ("GET".equals(requestMethod)) {
                            besitztController.handleGetBesitzt(exchange);
                        } else if ("POST".equals(requestMethod)) {
                            besitztController.handlePostBesitzt(exchange, requestBody);
                        }
                        break;
                    case AUSSTATTUNG_URL:
                        if ("GET".equals(requestMethod)) {
                            ausstattungController.handleGetAusstattungen(exchange);
                        } else if ("POST".equals(requestMethod)) {
                            ausstattungController.handlePostAusstattung(exchange, requestBody);
                        }
                        break;
                    case LAND_URL:
                        if ("GET".equals(requestMethod)) {
                            landController.handleGetLaender(exchange);
                        } else if ("POST".equals(requestMethod)) {
                            landController.handlePostLand(exchange, requestBody);
                        }
                        break;
                    case BUCHUNG_URL:
                        if ("GET".equals(requestMethod)) {
                            buchungController.handleGetBuchungen(exchange);
                        } else if ("POST".equals(requestMethod)) {
                            buchungController.handlePostBuchung(exchange, requestBody);
                        }
                        break;
                    default:
                        sendErrorResponse(exchange, 404, "Route not found");
                        return;
                }
            } catch (Exception e) {
                sendErrorResponse(exchange, 500, "Internal Server Error");
                e.printStackTrace();
            } finally {
                exchange.close();
            }
        }

        private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(statusCode, message.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream responseBody = exchange.getResponseBody()) {
                responseBody.write(message.getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
