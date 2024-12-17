package com.endgame.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

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
    public String payload;  // incoming_payload, error, outgoing_requests, outgoing_response
    public int responseCode = 200;
}
