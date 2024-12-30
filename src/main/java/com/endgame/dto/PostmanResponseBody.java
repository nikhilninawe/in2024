package com.endgame.dto;

import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class PostmanResponseBody {
    ResponseEntity<String> responseEntity;
    int responseCode;
    String requestHistoryId;
    public PostmanResponseBody(ResponseEntity<String> responseEntity, int responseCode, String requestHistoryId) {
        this.responseEntity = responseEntity;
        this.responseCode = responseCode;
        this.requestHistoryId = requestHistoryId;
    }
}
