package com.endgame.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class PostmanRequestHistory {
    @Id
    String id;
    RequestData request;
    long timestamp;
    String username;

    public PostmanRequestHistory(String username, RequestData request, long timestamp) {
        this.request = request;
        this.timestamp = timestamp;
        this.username = username;
    }
}