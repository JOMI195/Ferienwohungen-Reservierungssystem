package de.htwg.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Collectors;

import de.htwg.controller.KundeController;

public class HttpServer {
    private int port;
    private KundeController kundeController;
    private static String BASEURL = "/api";

    public HttpServer(int port) {
        this.port = port;
        this.kundeController = new KundeController();
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
                        }
                    }

                    if (requestLine.startsWith("GET " + BASEURL + "/kunde")) {
                        kundeController.handleGetKunden(out);
                    } else if (requestLine.startsWith("POST " + BASEURL + "/kunde")) {
                        if (requestBody != null) {
                            kundeController.handlePostKunde(out, requestBody);
                        } else {
                            out.write("HTTP/1.1 400 Bad Request\r\n\r\n".getBytes("UTF-8"));
                        }
                    } else {
                        out.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes("UTF-8"));
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
