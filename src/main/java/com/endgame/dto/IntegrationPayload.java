package com.endgame.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public String payload;  // incoming_payload, error, outgoing_requests, outgoing_response
    public int responseCode = 200;
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static IntegrationPayload createIntegrationPayload(LogMessage logMessage) throws ParseException {
        IntegrationPayload integrationPayload = new IntegrationPayload();
        integrationPayload.nonce = logMessage.getCorrelationId();
        integrationPayload.businessId = logMessage.getBusiness_id();
        integrationPayload.userId = logMessage.getUser_id();
        integrationPayload.payload = logMessage.getMessage();
        integrationPayload.timestamp = formatter.parse(logMessage.getTimestamp());
        return integrationPayload;
    }
}
