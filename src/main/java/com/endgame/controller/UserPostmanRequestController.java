package com.endgame.controller;

import com.endgame.dto.UserPostmanRequest;
import com.endgame.repository.UserPostmanRequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200/", "https://9406-2405-201-301d-e85d-6912-a330-d924-84f9.ngrok-free.app/",
        "https://stage-swift.turvo.net/", "https://7f2b-2405-201-301d-e85d-1d0b-19b3-2676-b6c6.ngrok-free.app/"})
public class UserPostmanRequestController {

    UserPostmanRequestsRepository repository;

    public UserPostmanRequestController(@Autowired UserPostmanRequestsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/request")
    public List<UserPostmanRequest> getUserPostmanRequests(@RequestParam(value = "username") String username) {
        return repository.findByUsername(username);
    }

    @PostMapping("/api/request")
    public UserPostmanRequest saveUserPostmanRequests(@RequestBody UserPostmanRequest request) {
        return repository.save(new UserPostmanRequest(request.getUsername(), request.getRequest(), request.getName()));
    }

    @DeleteMapping("/api/request")
    public void deleteUserPostmanRequests(@RequestParam(value = "id") String id) {
        repository.deleteById(id);
    }

    @PutMapping("/api/request")
    public UserPostmanRequest updateUserPostmanRequests(@RequestBody UserPostmanRequest request) {
        return repository.save(request);
    }

    @PutMapping("/api/shareRequest")
    public UserPostmanRequest shareRequest(@RequestParam String requestId, @RequestParam String sharedBy, @RequestParam String sharedTo) {
        UserPostmanRequest request = repository.findById(requestId).get();
        UserPostmanRequest sharedRequest = new UserPostmanRequest(sharedTo, request.getRequest(), request.getName());
        sharedRequest.setSharedBy(sharedBy);
        return repository.save(sharedRequest);
    }
}