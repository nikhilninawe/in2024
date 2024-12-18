package com.endgame.dto;

import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class PostmanResponseBody {
    ResponseEntity<String> responseEntity;
    int responseCode;
    public PostmanResponseBody(ResponseEntity<String> responseEntity, int responseCode) {
        this.responseEntity = responseEntity;
        this.responseCode = responseCode;
    }
}
