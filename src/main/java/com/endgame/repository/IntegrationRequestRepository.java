package com.endgame.repository;

import com.endgame.dto.IntegrationRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IntegrationRequestRepository extends MongoRepository<IntegrationRequest, String> {
    List<IntegrationRequest> findByBusinessId(long businessId);
    List<IntegrationRequest> findByTimestampBetweenAndBusinessId(long start, long end, long businessId);
}