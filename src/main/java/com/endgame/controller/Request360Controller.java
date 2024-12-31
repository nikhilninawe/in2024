package com.endgame.controller;

import com.endgame.dto.IntegrationPayload;
import com.endgame.repository.IntegrationRequestRepository;
import com.endgame.service.Request360Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://9406-2405-201-301d-e85d-6912-a330-d924-84f9.ngrok-free.app/",
                        "https://stage-swift.turvo.net/"})
public class Request360Controller {
    private final IntegrationRequestRepository repository;
    private final Request360Service service;

    public Request360Controller(@Autowired IntegrationRequestRepository requestRepository,
                                @Autowired Request360Service service) {
        this.repository = requestRepository;
        this.service = service;
    }

//    @PostConstruct
    public void init() {
        repository.deleteAll();
        IntegrationPayload request = new IntegrationPayload();
        request.businessId = 109;
        request.context = "SHIPMENT";
        request.method = "POST";
        request.timestamp = new Date();
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
        return service.getIntegrationRequest(nonce);
    }

    @PostMapping("/integration_request")
    public ResponseEntity<HttpStatus> postIntegrationRequest(@RequestBody IntegrationPayload request) {
        repository.save(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
