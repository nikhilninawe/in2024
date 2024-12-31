package com.endgame.service;

import com.endgame.dto.IntegrationPayload;
import com.endgame.dto.LogMessage;
import com.endgame.repository.IntegrationRequestRepository;
import com.endgame.repository.LogMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class Request360Service {

    private IntegrationRequestRepository repository;
    private LogMessageRepository logMessageRepository;

    public Request360Service(@Autowired IntegrationRequestRepository repository,
                             @Autowired LogMessageRepository logMessageRepository) {
        this.repository = repository;
        this.logMessageRepository = logMessageRepository;
    }

    public List<IntegrationPayload> getIntegrationRequest(String nonce) {
        List<IntegrationPayload> integrationPayloads = repository.findByNonce(nonce);
        if(integrationPayloads != null && !integrationPayloads.isEmpty()) {
            return integrationPayloads;
        }
        integrationPayloads = new ArrayList<>();
        List<LogMessage> logMessage = logMessageRepository.findByCorrelationId(nonce);
        for(LogMessage log : logMessage) {
            if(Objects.isNull(log)){
                continue;
            }
            try{
                IntegrationPayload integrationPayload = IntegrationPayload.createIntegrationPayload(log);
                integrationPayloads.add(integrationPayload);
            }catch (Exception ignored){
                System.out.println(ignored);
            }
        }
        return integrationPayloads;
    }
}
