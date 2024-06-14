package de.htwg.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class CustomResponseBuilder {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_INSTANT;

    public static String buildSuccessResponse(Object data) {
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("status", "success");
        rootNode.putPOJO("data", data);
        return rootNode.toPrettyString();
    }

    public static String buildErrorResponse(int statusCode, String errorCode, String message, String request) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("code", errorCode);
        errorNode.put("message", message);
        errorNode.put("timestamp", ZonedDateTime.now().format(dateTimeFormatter));
        errorNode.put("request", request);

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("status", "error");
        rootNode.put("statusCode", statusCode);
        rootNode.set("error", errorNode);

        return rootNode.toPrettyString();
    }
}
