package com.endgame.controller;

import com.endgame.dto.RequestData;
import com.endgame.dto.UserPostmanRequest;
import com.endgame.repository.UserPostmanRequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserPostmanRequestController {

    UserPostmanRequestsRepository repository;

    public UserPostmanRequestController(@Autowired UserPostmanRequestsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/requests")
    public List<UserPostmanRequest> getUserPostmanRequests(@RequestParam(value = "username") String username) {
        return repository.findByUsername(username);
    }

    @PostMapping("/api/request")
    public UserPostmanRequest saveUserPostmanRequests(@RequestParam(value = "username") String username,
                                                      @RequestBody RequestData request) {
        return repository.save(new UserPostmanRequest(username, request));
    }
}