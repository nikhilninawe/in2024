package com.endgame.repository;

import com.endgame.dto.LogMessage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LogMessageRepository extends ElasticsearchRepository<LogMessage, String> {
    List<LogMessage> findByCorrelationId(String correlationId);
}
