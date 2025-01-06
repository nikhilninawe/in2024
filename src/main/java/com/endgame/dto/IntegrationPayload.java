package com.endgame.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Data
public class IntegrationPayload {
    @Id
    public String id;

    public long businessId;
    public Date timestamp;
    public long userId;
    public String context;
    public String method;
    public String nonce;
    public String entityId;
    public String severity;
    public String payload;  // incoming_payload, error, outgoing_requests, outgoing_response
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static IntegrationPayload createIntegrationPayload(LogMessage logMessage) throws ParseException {
        IntegrationPayload integrationPayload = new IntegrationPayload();
        integrationPayload.nonce = logMessage.getCorrelationId();
        integrationPayload.businessId = logMessage.getBusiness_id();
        integrationPayload.userId = logMessage.getUser_id();
        String[] array = logMessage.getMessage().split(" ");
        String[] message = Arrays.copyOfRange(array, 7, array.length);
        integrationPayload.payload = String.join(" ", message);
        integrationPayload.timestamp = formatter.parse(logMessage.getTimestamp());
        integrationPayload.severity = logMessage.getSeverity();
        return integrationPayload;
    }
}
