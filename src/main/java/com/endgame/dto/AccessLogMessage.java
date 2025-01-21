package com.endgame.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Document(indexName = "logstash-*")
public class AccessLogMessage {
    @Id
    private String id;
    @Field(name = "correlation_id")
    private String correlationId;
    @Field(name = "business_id")
    private int businessId;
    private String role;
    @Field(name = "@timestamp")
    private String timestamp;
    private String host;
    private String thread;
    private String environment;
    private String method;
    private String path;
    @Field(name = "response_code")
    private int responseCode;
    @Field(name = "response_time")
    private int responseTime;
    private String target;
    private String query;
}
