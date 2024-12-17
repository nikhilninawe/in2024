package com.endgame.repository;

import com.endgame.dto.UserPostmanRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserPostmanRequestsRepository extends MongoRepository<UserPostmanRequest, String> {
    List<UserPostmanRequest> findByUsername(String username);
}
