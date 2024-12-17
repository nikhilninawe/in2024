package com.endgame.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserPostmanRequest {
    @Id
    String id;
    public String username;
    public RequestData request;

    public UserPostmanRequest(String username, RequestData request) {
        this.username = username;
        this.request = request;
    }
}
