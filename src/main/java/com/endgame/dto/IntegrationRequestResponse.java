package com.endgame.dto;

import lombok.Data;

@Data
public class IntegrationRequestResponse {

    public String requestUrl;
    public String requestPayload;
    public String responseCode;
    public String responsePayload;
}
