package com.endgame.repository;

import com.endgame.dto.AccessLogMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AccessLogRepository extends ElasticsearchRepository<AccessLogMessage, String> {

    List<AccessLogMessage> findByCorrelationId(String correlationId);
    @Query("{" +
            "  \"bool\": {" +
            "    \"must\": [" +
            "      {\"wildcard\": {\"target\": \"*pub*\"}}," +
            "      {\"wildcard\": {\"target\": \"*?0*\"}}" +
            "    ]," +
            "    \"filter\": [" +
//            "      {\"term\": {\"role\": \"?1\"}}," +
            "      {\"term\": {\"business_id\": ?2}}," +
            "      {\"term\": {\"environment\": \"?3\"}}," +
            "      {\"term\": {\"type\": \"access\"}}," +
            "      {\"term\": {\"method\": \"?4\"}}" +
            "    ]" +
            "  }" +
            "}")
    List<AccessLogMessage> findLastTenAccessLogs(String target, String role, int businessId, String environment,
                                                 String method,Pageable pageable);
}
