package com.endgame.controller;

import com.endgame.dto.IntegrationPayload;
import com.endgame.dto.IntegrationRequestResponse;
import com.endgame.repository.IntegrationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class IntegrationDebugController {
    private final IntegrationRequestRepository repository;

    public IntegrationDebugController(@Autowired IntegrationRequestRepository requestRepository) {
        this.repository = requestRepository;
    }

//    @PostConstruct
    public void init() {
        repository.deleteAll();
        IntegrationPayload request = new IntegrationPayload();
        request.businessId = 109;
        request.context = "SHIPMENT";
        request.method = "POST";
        request.timestamp = 1734080330389L;
        request.userId = 9687;
        request.nonce = "f190075a-9476-47e4-89fc-26dab1715d70";
        request.payload = "{\"id\":87049652,\"_operation\":1,\"globalRoute\":[{\"id\":210255621,\"segmentSequence\":0,\"sequence\":0,\"appointment\":{\"date\":\"2024-12-13T14:00:00Z\",\"timeZone\":\"America/New_York\",\"flex\":1062000,\"hasTime\":true},\"deleted\":false,\"_operation\":1},{\"id\":210255622,\"segmentSequence\":0,\"sequence\":1,\"appointment\":{\"date\":\"2024-12-14T14:00:00Z\",\"timeZone\":\"America/New_York\",\"flex\":1062000,\"hasTime\":true},\"deleted\":false,\"_operation\":1}],\"use_routing_guide\":false,\"shipmentCheckId\":0}".replace("\"", "");;
        repository.save(request);
    }

    @GetMapping("/integration_request")
    public List<IntegrationPayload> getIntegrationRequest(@RequestParam(value = "busId") long busId) {
        return repository.findByBusinessId(busId);
    }

    @GetMapping("/integration_request_between_timestamps")
    public List<IntegrationPayload> getIntegrationRequestBetweenTimestamps(@RequestParam(value = "start") long start,
                                                                           @RequestParam(value = "end") long end,
                                                                           @RequestParam(value = "busId") long busId) {
        return repository.findByTimestampBetweenAndBusinessId(start, end, busId);
    }

    @GetMapping("/integration_request_by_nonce")
    public List<IntegrationPayload> getIntegrationRequestByNonce(@RequestParam(value = "nonce") String nonce) {
        return repository.findByNonce(nonce);
    }

    @PostMapping("/integration_request")
    public ResponseEntity<HttpStatus> postIntegrationRequest(@RequestBody IntegrationPayload request) {
        repository.save(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
