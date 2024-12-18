package com.endgame.dto;

import lombok.Data;
import org.bson.json.JsonObject;

import java.util.Map;

@Data
public class RequestData {
    String url;
    Object body;
    String method;
    Map<String, String> headers;
}
