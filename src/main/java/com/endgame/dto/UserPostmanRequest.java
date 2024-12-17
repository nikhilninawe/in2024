package com.endgame.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserPostmanRequest {
    @Id
    String id;
    public String username;
    public RequestData request;
    public String name;
    public String sharedBy; //by default null

    public UserPostmanRequest(String username, RequestData request, String name) {
        this.username = username;
        this.request = request;
        this.name = name;
    }
}
