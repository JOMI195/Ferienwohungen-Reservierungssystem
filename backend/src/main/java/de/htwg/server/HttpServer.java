package de.htwg.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import de.htwg.controller.AusstattungController;
import de.htwg.controller.BesitztController;
import de.htwg.controller.BuchungController;
import de.htwg.controller.FerienwohnungController;
import de.htwg.controller.KundeController;
import de.htwg.controller.LandController;

public class HttpServer {
    private int port;
    private static String BASEURL = "/api";

    private static final String kundeUrl = "/kunde";
    private static final String ferienwohnungUrl = "/ferienwohnung";
    private static final String besitztUrl = "/besitzt";
    private static final String ausstattungUrl = "/ausstattung";
    private static final String landUrl = "/land";
    private static final String buchungUrl = "/buchung";

    private KundeController kundeController;
    private FerienwohnungController ferienwohnungController;
    private BesitztController besitztController;
    private AusstattungController ausstattungController;
    private LandController landController;
    private BuchungController buchungController;

    public HttpServer(int port) {
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
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(port));
            System.out.println("Server started on port " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
                        OutputStream out = clientSocket.getOutputStream()) {

                    String requestLine = in.readLine();
                    if (requestLine == null || !(requestLine.startsWith("GET") || requestLine.startsWith("POST"))) {
                        out.write("HTTP/1.1 400 Bad Request\r\n\r\n".getBytes("UTF-8"));
                        continue;
                    }

                    String headers = in.lines().takeWhile(line -> !line.isEmpty()).collect(Collectors.joining("\n"));

                    System.out.println("Request Line: " + requestLine);
                    System.out.println("Headers: " + headers);

                    String requestBody = null;
                    if (requestLine.startsWith("POST")) {
                        int contentLength = getContentLength(headers);
                        if (contentLength > 0) {
                            char[] bodyChars = new char[contentLength];
                            in.read(bodyChars);
                            requestBody = new String(bodyChars);
                            System.out.println("Request Body: " + requestBody);
                        } else {
                            String errorResponse = CustomResponseBuilder.buildErrorResponse(
                                    400,
                                    "BAD_REQUEST",
                                    "No Request Body for POST provided",
                                    requestLine);
                            String response = "HTTP/1.1 400 Bad Request\r\nContent-Type: text/plain; charset=UTF-8\r\n\r\n"
                                    + errorResponse;
                            out.write(response.getBytes(StandardCharsets.UTF_8));
                            continue;
                        }
                    }

                    /* ---------------------KUNDE--------------------- */

                    if (requestLine.startsWith("GET " + BASEURL + kundeUrl)) {
                        kundeController.handleGetKunden(out);
                    } else if (requestLine.startsWith("POST " + BASEURL + kundeUrl)) {
                        kundeController.handlePostKunde(out, requestBody);
                        /* ---------------------FERIENWOHUNUNG--------------------- */
                    } else if (requestLine.startsWith("GET " + BASEURL + ferienwohnungUrl)) {
                        ferienwohnungController.handleGetFerienwohnungen(out);
                    } else if (requestLine.startsWith("POST " + BASEURL + ferienwohnungUrl)) {
                        ferienwohnungController.handlePostFerienwohnung(out, requestBody);
                        /* ---------------------BESITZT--------------------- */
                    } else if (requestLine.startsWith("GET " + BASEURL + besitztUrl)) {
                        besitztController.handleGetBesitzt(out);
                    } else if (requestLine.startsWith("POST " + BASEURL + besitztUrl)) {
                        besitztController.handlePostBesitzt(out, requestBody);
                        /* ---------------------AUSSTATTUNG--------------------- */
                    } else if (requestLine.startsWith("GET " + BASEURL + ausstattungUrl)) {
                        ausstattungController.handleGetAusstattungen(out);
                    } else if (requestLine.startsWith("POST " + BASEURL + ausstattungUrl)) {
                        ausstattungController.handlePostAusstattung(out, requestBody);
                        /* ---------------------LAND--------------------- */
                    } else if (requestLine.startsWith("GET " + BASEURL + landUrl)) {
                        landController.handleGetLaender(out);
                    } else if (requestLine.startsWith("POST " + BASEURL + landUrl)) {
                        landController.handlePostLand(out, requestBody);
                        /* ---------------------BUCHUNG--------------------- */
                    } else if (requestLine.startsWith("GET " + BASEURL + buchungUrl)) {
                        buchungController.handleGetBuchungen(out);
                    } else if (requestLine.startsWith("POST " + BASEURL + buchungUrl)) {
                        buchungController.handlePostBuchung(out, requestBody);
                    } else {
                        String errorResponse = CustomResponseBuilder.buildErrorResponse(
                                404,
                                "ROUTE_NOT_FOUND",
                                "The requested route was not found.",
                                requestLine);
                        String response = "HTTP/1.1 404 Not Found\r\nContent-Type: text/plain; charset=UTF-8\r\n\r\n"
                                + errorResponse;
                        out.write(response.getBytes(StandardCharsets.UTF_8));
                        continue;
                    }
                }
            }
        }
    }

    private int getContentLength(String headers) {
        String[] lines = headers.split("\n");
        for (String line : lines) {
            if (line.toLowerCase().startsWith("content-length")) {
                String[] tokens = line.split(":");
                return Integer.parseInt(tokens[1].trim());
            }
        }
        return 0;
    }
}
