package com.endgame.dto;

import lombok.Data;
import java.util.Map;

@Data
public class PostmanRequest {
    RequestData requestData;
    String username;
    String environment;
}
