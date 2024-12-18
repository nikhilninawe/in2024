package com.endgame.controller;

import com.endgame.dto.PostmanRequest;
import com.endgame.dto.PostmanRequestHistory;
import com.endgame.dto.PostmanResponseBody;
import com.endgame.repository.PostmanRequestHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://9406-2405-201-301d-e85d-6912-a330-d924-84f9.ngrok-free.app/",
        "https://stage-swift.turvo.net/"})
public class PostmanController {

    RestTemplate restTemplate = new RestTemplate();
    PostmanRequestHistoryRepository postmanRequestHistoryRepository;

    public PostmanController(@Autowired PostmanRequestHistoryRepository repository) {
        this.postmanRequestHistoryRepository = repository;
    }

    @GetMapping("/api/history")
    public List<PostmanRequestHistory> history(@RequestParam(value = "username") String username) {
        return postmanRequestHistoryRepository.findByUsername(username);
    }

    @PostMapping("/api/execute")
    public @ResponseBody PostmanResponseBody execute(@RequestBody PostmanRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (String key : request.getRequestData().getHeaders().keySet()) {
            httpHeaders.add(key, request.getRequestData().getHeaders().get(key));
        }
        HttpEntity<Object> httpEntity = new HttpEntity<>(request.getRequestData().getBody(), httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(request.getRequestData().getUrl(),
                HttpMethod.valueOf(request.getRequestData().getMethod()), httpEntity, String.class);
        postmanRequestHistoryRepository.save(new PostmanRequestHistory(request.getUsername(),
                request.getRequestData(), System.currentTimeMillis()));
        return new PostmanResponseBody(responseEntity, responseEntity.getStatusCode().value());
    }
}
