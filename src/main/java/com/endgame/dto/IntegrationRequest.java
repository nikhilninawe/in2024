package com.endgame.dto;

import org.springframework.data.annotation.Id;

import java.util.List;

public class IntegrationRequest {
    @Id
    public String id;

    public long businessId;
    public long timestamp;
    public long userId;
    public String context;
    public String method;
    public String nonce;
    public String payload;
    public List<String> errors;
    public List<IntegrationRequestResponse> integrationRequestResponses;
}
