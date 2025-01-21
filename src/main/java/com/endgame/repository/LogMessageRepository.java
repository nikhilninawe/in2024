package com.endgame.repository;

import com.endgame.dto.LogMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LogMessageRepository extends ElasticsearchRepository<LogMessage, String> {

    // Updated query method to include correlationId
    @Query("{" +
            "  \"bool\": {" +
            "    \"must\": [" +
            "      {\"wildcard\": {\"class\": \"*ContextHelper\"}}," +
            "      {\"match_phrase\": {\"message\": \"request payload\"}}," +
            "      {\"terms\": {\"role\": [\"api\", \"orders\", \"marketplace\"]}}," +
            "      {\"term\": {\"correlation_id\": \"?0\"}}" +
            "    ]," +
            "    \"filter\": [" +
            "      {\"term\": {\"environment\": \"?1\"}}" +
            "    ]" +
            "  }" +
            "}")
    List<LogMessage> findPayloadByCorrelationId(String correlationId, String environment);

    List<LogMessage> findByCorrelationIdAndSeverityAndRoleAndEnvironment(String correlationId, String severity, String role,String environment);

    @Query("{" +
            "  \"bool\": {" +
            "    \"must\": [" +
            "      {\"wildcard\": {\"class\": \"*ContextHelper\"}}," +
            "      {\"match_phrase\": {\"message\": \"request payload\"}}," +
            "      {\"terms\": {\"role\": [\"api\", \"orders\", \"marketplace\"]}}" +
            "    ]," +
            "    \"filter\": [" +
            "      {\"term\": {\"business_id\": \"?0\"}}," +
            "      {\"term\": {\"environment\": \"?1\"}}" +
            "    ]" +
            "  }" +
            "}")
    List<LogMessage> findLastTenPayloadOrderByTimestamp(int businessId, String environment);

    @Query("{" +
            "  \"bool\": {" +
            "    \"must\": [" +
            "      {\"wildcard\": {\"class\": \"*ContextHelper\"}}," +
            "      {\"match_phrase\": {\"message\": \"request payload\"}}" +
            "    ]," +
            "    \"filter\": [" +
            "      {\"term\": {\"business_id\": ?0}}," +
            "      {\"term\": {\"environment\": \"?1\"}}," +
            "      {\"terms\": {\"role\": [\"api\", \"orders\", \"marketplace\"]}}" +
            "    ]" +
            "  }" +
            "}")
    List<LogMessage> findLasTenPayload(
            int businessId,
            String environment,
            Pageable pageable
    );

    @Query("{" +
            "  \"bool\": {" +
            "    \"must\": [" +
            "      {\"match_phrase\": {\"message\": \"?4\"}}" +
            "    ]," +
            "    \"filter\": [" +
            "      {\"term\": {\"host\": \"?0\"}}," +
            "      {\"term\": {\"thread\": \"?1\"}}," +
            "      {\"term\": {\"class\": \"?2\"}}," +
            "      {\"term\": {\"environment\": \"?3\"}}" +
            "    ]" +
            "  }" +
            "}")
    List<LogMessage> findByHostAndThreadAndClassNameAndEnvironmentAndTarget(String host, String thread, String className, String environment, String target, Pageable pageable);
}
