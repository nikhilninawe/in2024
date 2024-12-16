package com.endgame.dto;

import org.springframework.data.annotation.Id;

public class IntegrationPayload {
    @Id
    public String id;

    public long businessId;
    public long timestamp;
    public long userId;
    public String context;
    public String method;
    public String nonce;
    public String payload;  // incoming_payload, error, outgoing_requests, outgoing_response
    public int responseCode;
}
