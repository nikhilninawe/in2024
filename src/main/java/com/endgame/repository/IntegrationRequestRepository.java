package com.endgame.repository;

import com.endgame.dto.IntegrationPayload;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IntegrationRequestRepository extends MongoRepository<IntegrationPayload, String> {
    List<IntegrationPayload> findByBusinessId(long businessId);
    List<IntegrationPayload> findByTimestampBetweenAndBusinessId(long start, long end, long businessId);
    List<IntegrationPayload> findByNonce(String nonce);
    List<IntegrationPayload> findByNonceOrEntityId(String nonce, String entityId);
}