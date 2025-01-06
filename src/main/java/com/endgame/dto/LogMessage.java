package com.endgame.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Document(indexName = "logstash-*")
public class LogMessage {

    @Id
    private String id;
    private String message;
    @Field(name = "correlation_id")
    private String correlationId;
    private int business_id;
    private int user_id;
    private String role;
    @Field(name = "@timestamp")
    private String timestamp;
    private String severity;
}
