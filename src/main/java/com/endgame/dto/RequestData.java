package com.endgame.dto;

import lombok.Data;

import java.util.Map;

@Data
public class RequestData {
    String url;
    String body;
    String method;
    Map<String, String> headers;
}
