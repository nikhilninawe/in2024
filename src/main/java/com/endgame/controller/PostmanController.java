package com.endgame.controller;

import com.endgame.dto.PostmanRequest;
import com.endgame.dto.PostmanRequestHistory;
import com.endgame.dto.PostmanResponseBody;
import com.endgame.repository.PostmanRequestHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200/", "https://9406-2405-201-301d-e85d-6912-a330-d924-84f9.ngrok-free.app/",
        "https://stage-swift.turvo.net/", "https://7f2b-2405-201-301d-e85d-1d0b-19b3-2676-b6c6.ngrok-free.app/"})
public class PostmanController {

    RestTemplate restTemplate = new RestTemplate();
    PostmanRequestHistoryRepository postmanRequestHistoryRepository;

    public PostmanController(@Autowired PostmanRequestHistoryRepository repository) {
        this.postmanRequestHistoryRepository = repository;
        restTemplate.getMessageConverters().add(getMappingJackson2HttpMessageConverter());

    }

    @GetMapping("/api/history")
    public List<PostmanRequestHistory> history(@RequestParam(value = "username") String username) {
        return postmanRequestHistoryRepository.findByUsername(username);
    }

    @DeleteMapping("/api/history")
    public void deleteHistory(@RequestParam(value = "id") String id) {
        postmanRequestHistoryRepository.deleteById(id);
    }

    @PostMapping("/api/execute")
    public @ResponseBody PostmanResponseBody execute(@RequestBody PostmanRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (String key : request.getRequestData().getHeaders().keySet()) {
            httpHeaders.add(key, request.getRequestData().getHeaders().get(key));
        }

        Object body = request.getRequestData().getBody();
        System.out.println(body.getClass());
//        if(request.getRequestData().getHeaders().get("Content-Type").contains("application/x-www-form-urlencoded")){
//            MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
//            requestBody.setAll((LinkedMultiValueMap<String, Object>) body);
//            body = requestBody;
//        }

        HttpEntity<Object> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> responseEntity;
        try {
             responseEntity = restTemplate.exchange(request.getRequestData().getUrl(),
                    HttpMethod.valueOf(request.getRequestData().getMethod()), httpEntity, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            responseEntity = new ResponseEntity<>(ex.getResponseBodyAsString(), ex.getStatusCode());
            PostmanRequestHistory history = postmanRequestHistoryRepository.save(new PostmanRequestHistory(request.getUsername(),
                    request.getRequestData(), System.currentTimeMillis()));
            return new PostmanResponseBody(responseEntity, ex.getStatusCode().value(), history.getId());
        }
        PostmanRequestHistory history = postmanRequestHistoryRepository.save(new PostmanRequestHistory(request.getUsername(),
                request.getRequestData(), System.currentTimeMillis()));
        return new PostmanResponseBody(responseEntity, responseEntity.getStatusCode().value(), history.getId());
    }

    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
        return mappingJackson2HttpMessageConverter;
    }
}
