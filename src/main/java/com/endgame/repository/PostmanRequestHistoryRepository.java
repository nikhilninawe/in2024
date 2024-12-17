package com.endgame.repository;

import com.endgame.dto.PostmanRequestHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostmanRequestHistoryRepository extends MongoRepository<PostmanRequestHistory, String> {
    List<PostmanRequestHistory> findByUsername(String username);
}